package dpbo.bimble;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    private static Auth auth = new Auth();
    private static List<Schedule> schedules = new ArrayList<>();
    private static List<Registrasi> registrations = new ArrayList<>();
    private static List<Notification> notifications = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== BRITON BIMBEL =====");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            int choice = MissionUtil.getInt("Pilih menu: ");

            switch (choice) {
                case 1:
                    loginMenu();
                    break;
                case 2:
                    System.out.println("Terima kasih.");
                    return;
                default:
                    System.out.println("Pilihan salah.");
            }
        }
    }

    private static void loginMenu() {
        System.out.println("\n--- LOGIN ---");
        String username = MissionUtil.getString("Username");
        String password = MissionUtil.getPassword("Password");

        User user = auth.loginUser(username, password);
        if (user == null) {
            System.out.println("Login gagal.");
            return;
        }

        user.login();
        if (user instanceof Admin) {
            adminMenu((Admin) user);
        } else if (user instanceof Student) {
            studentMenu((Student) user);
        } else {
            System.out.println("Peran tidak dikenali.");
        }
    }

    private static void adminMenu(Admin admin) {
        while (true) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. Tambah Jadwal");
            System.out.println("2. Lihat Semua Registrasi");
            System.out.println("3. Logout");

            int pil = MissionUtil.getInt("Pilih menu: ");
            switch (pil) {
                case 1:
                    tambahJadwal();
                    break;
                case 2:
                    lihatRegistrasi();
                    break;
                case 3:
                    admin.logout();
                    return;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void studentMenu(Student student) {
        while (true) {
            System.out.println("\n=== STUDENT MENU ===");
            System.out.println("1. Daftar Kursus");
            System.out.println("2. Lihat Notifikasi");
            System.out.println("3. Lihat Schedule");
            System.out.println("4. Logout");

            int pilihan = MissionUtil.getInt("Pilih menu: ");
            switch (pilihan) {
                case 1:
                	Scanner scanner = new Scanner(System.in);
                	Registrasi reg = student.registrasi();
                	if (reg != null) {
                	    registrations.add(reg);
                	    Notification notif = new Notification(
                	    	    "NTF-1", 
                	    	    "Pendaftaran berhasil", 
                	    	    "Registrasi", 
                	    	    reg, 
                	    	    "Belum dibayar", 
                	    	    null, null, null
                	    	);
                	    notifications.add(notif);
                	    System.out.println("Registrasi berhasil.\n" + reg);
                	}
                    break;
                case 2:
                    System.out.println("\n--- Notifikasi ---");
                    for (Notification n : notifications) {
                        if (n.getRegistration().getNamaLengkap().equals(student.getUsername())) {
                            System.out.println("- " + n.getMessage() + " [" + n.getPaymentStatus() + "]");
                        }
                    }
                    break;
                case 3:
                    student.seeSchedule(schedules);
                    break;
                case 4:
                    student.logout();
                    return;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void tambahJadwal() {
        System.out.println("\n--- Tambah Jadwal ---");
        String subject = MissionUtil.getString("Mata Pelajaran");
        String date = MissionUtil.getString("Tanggal (YYYY-MM-DD)");
        String start = MissionUtil.getString("Jam Mulai");
        String end = MissionUtil.getString("Jam Selesai");
        String room = MissionUtil.getString("Ruangan");

        Schedule schedule = new Schedule(subject, date, start, end, room);
        schedules.add(schedule);
        System.out.println("Jadwal berhasil ditambahkan.");
    }

    private static void lihatRegistrasi() {
        System.out.println("\n--- Daftar Registrasi ---");
        if (registrations.isEmpty()) {
            System.out.println("Belum ada registrasi.");
            return;
        }

        for (Registrasi r : registrations) {
            System.out.println(r);
        }
    }
}
