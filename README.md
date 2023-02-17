# Tubes1_ShotaLovers6969

Pemanfaatan Algoritma Greedy dalam Aplikasi Permainan “Galaxio”

Program Ini Dibuat Untuk Memenuhi Tugas Perkuliahan Mata Kuliah Strategi Algoritma (IF2211)

Program Studi Teknik Informatika
Sekolah Teknik Elektro dan Informatika
Institut Teknologi Bandung
Semester II Tahun 2022/2023

## General Information

Galaxio adalah sebuah game battle royale yang mempertandingkan bot kapal anda dengan beberapa bot kapal yang lain. Setiap pemain akan memiliki sebuah bot kapal dan tujuan dari permainan adalah agar bot kapal anda yang tetap hidup hingga akhir permainan. Agar dapat memenangkan pertandingan, setiap bot harus mengimplementasikan strategi tertentu untuk dapat memenangkan permainan.


Ini merupakan program sederhana dalam Bahasa Java yang mengimplementasikan Algoritma Greedy dalam Aplikasi Permainan “Galaxio”. Kami memilih tiga buah strategi algoritma yang digunakan berdasarkan prioritas: Escaping, Chasing, dan Fetching. Kapal akan berusaha "kabur" dari berbagai bahaya yang mengancam kapal tersebut. Jika bahaya hilang, barulah kapal kami mulai menyerang kapal lawan. Jika kapal kami dalam posisi yang menyusahkan (seperti ukuran yang lebih kecil dari kapal lawan) dan tidak ada bahaya pada tick tertentu, kapal akan berusaha melakukan feeding terhadap food sebanyak mungkin.

## Penjelasan Singkat Algoritma Greedy yang Diimplementasikan
Tujuan dari setiap pemain dalam permainan Galaxio ini adalah untuk
bertahan sampai menjadi pemain tersisa terakhir. Terdapat berbagai
cara untuk menjadi pemain terakhir yang bersisa, seperti menembakin
pemain lain, fokus mencari makan, dan lain lain. Dalam program ini,
bot akan berfokus mencari makan, namun ketika terdapat pemain lain
yang mungkin ditembak/dikejar, bot akan menembak/mengejar pamain
tersebut.

## Prerequisites
* Java versi 11 atau lebih
* NodeJS
* .Net Core 3.1
* Apache Maven

## Build Program
1. Clone repository ini
2. Buka terminal di dalam folder hasil clone
3. Jalankan perintah `mvn clean package`

## Run Program

## Author

- Muhammad Farrel Danendra Rachim (13521048)
- Haidar Hamda (13521105)
- Reza Pahlevi Ubaidillah (13521165)

