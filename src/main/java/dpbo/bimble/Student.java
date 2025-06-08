package dpbo.bimble;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class Student extends User {

	public Student(String id, String nama, String email, String password) {
	    super(id, nama, email, password);
	}


    @Override
    public void login() {
        System.out.println("Student '" + getUsername() + "' melakukan tindakan spesifik setelah login.");
        System.out.println("Contoh: Menampilkan halaman utama student.");
    }

    @Override
    public void logout() {
        System.out.println("Student '" + getUsername() + "' melakukan tindakan spesifik saat logout.");
    }
    
    public void seeSchedule(List<Schedule> schedules) {
        if (schedules.isEmpty()) {
            System.out.println("Belum ada jadwal tersedia.");
            return;
        }

        for (Schedule s : schedules) {
            s.printSchedule();
        }
    }
    
    public Registrasi registrasi() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Nama Lengkap: ");
            String nama = scanner.nextLine();
            if (nama.isEmpty()) throw new IllegalArgumentException("Nama tidak boleh kosong");

            System.out.print("Tanggal Lahir (yyyy-MM-dd): ");
            String tanggalStr = scanner.nextLine();
            java.sql.Date tanggalLahir; 
            try {
                java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(tanggalStr);
                tanggalLahir = new java.sql.Date(utilDate.getTime());
            } catch (ParseException e) {
                throw new IllegalArgumentException("Format tanggal salah, gunakan yyyy-MM-dd");
            }

            System.out.print("Jenis Kelamin (L/P): ");
            String gender = scanner.nextLine();
            if (!(gender.equalsIgnoreCase("L") || gender.equalsIgnoreCase("P")))
                throw new IllegalArgumentException("Jenis kelamin hanya L atau P");

            System.out.print("Alamat: ");
            String alamat = scanner.nextLine();
            if (alamat.isEmpty()) throw new IllegalArgumentException("Alamat tidak boleh kosong");

            System.out.print("No HP: ");
            String noHp = scanner.nextLine();
            if (noHp.isEmpty()) throw new IllegalArgumentException("No HP tidak boleh kosong");

            System.out.print("Email: ");
            String email = scanner.nextLine();
            if (!email.contains("@")) throw new IllegalArgumentException("Format email tidak valid");

            System.out.print("Nama Wali: ");
            String namaWali = scanner.nextLine();
            if (namaWali.isEmpty()) throw new IllegalArgumentException("Nama wali tidak boleh kosong");

            System.out.print("No HP Wali: ");
            String noHpWali = scanner.nextLine();
            if (noHpWali.isEmpty()) throw new IllegalArgumentException("No HP wali tidak boleh kosong");

            System.out.print("Metode Pembayaran (Cash/Transfer): ");
            String metodePembayaran = scanner.nextLine();
            if (!(metodePembayaran.equalsIgnoreCase("Cash") || metodePembayaran.equalsIgnoreCase("Transfer")))
                throw new IllegalArgumentException("Metode pembayaran harus Cash atau Transfer");

            return new Registrasi(this, nama, tanggalLahir, gender, alamat, noHp, email, namaWali, noHpWali, metodePembayaran);

        } catch (IllegalArgumentException e) {
            System.out.println("Terjadi kesalahan input: " + e.getMessage());
            return null;
        }
    }
}
