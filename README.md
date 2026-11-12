<div align="center">

<br/>
<br/>

# 🎵 RynzMusic

### Modern, Aesthetic & Multi-Themed YouTube Music Client for Android

<br/>

[![Kotlin](https://img.shields.io/badge/Kotlin-2.3.20-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-4285F4?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/jetpack/compose)
[![License](https://img.shields.io/badge/License-GPL--3.0-blue?style=for-the-badge)](LICENSE)

<br/>

[**Fitur Unggulan**](#fitur-unggulan) · [**Sistem Multi-Theme**](#sistem-multi-theme) · [**Cara Membuka di Android Studio**](#cara-membuka-di-android-studio) · [**Arsitektur**](#arsitektur)

<br/>

</div>

> [!NOTE]
> **RynzMusic** adalah aplikasi pemutar musik modern berbasis Android (Kotlin & Jetpack Compose) yang dikembangkan dengan tampilan antarmuka (UI/UX) baru, sistem **Custom Multi-Theme**, efek *frosted-glass* yang elegan, dan fitur pemutaran audio resolusi tinggi terintegrasi dengan YouTube Music.

---

## 🎨 Sistem Multi-Theme (Baru di RynzMusic)

RynzMusic dilengkapi fitur **Dynamic Multi-Theme Engine** yang memungkinkan Anda mengubah tampilan aplikasi secara instan langsung melalui menu **Settings → Appearance → Color Theme**:

| Tema | Aksen Warna | Deskripsi Tampilan |
| :--- | :---: | :--- |
| **Rynz Violet Glow** (Default) | `#8B5CF6` | Flagship dark theme dengan aksen neon violet, permukaan obsidian midnight, dan efek frosted glass mewah. |
| **Cyber Cyan** | `#00E5FF` | Nuansa cyberpunk futuristik dengan kontras cyan neon di atas dasar oceanic black. |
| **Emerald Aurora** | `#10B981` | Aksen hijau emerald bercahaya dengan ambient glow yang menyejukkan mata. |
| **Sunset Crimson** | `#FF2A55` | Aksen coral rose & crimson berenergi tinggi dengan latar dark wine. |
| **Electric Amber** | `#F59E0B` | Nuansa emas champagne hangat dipadu permukaan bronze gelap yang eksklusif. |
| **Midnight OLED** | `#FFFFFF` | Kontras murni monokrom dan platinum putih di atas latar hitam pekat hemat baterai (OLED). |

---

## 🚀 Fitur Unggulan

<table>
  <tr>
    <td width="50%" valign="top">

#### 🎧 Playback & Audio
- **Streaming Lengkap**: Cari, jelajahi, dan putar katalog lagu YouTube Music.
- **Hi-Res Lossless Audio**: Dukungan FLAC/ALAC dengan fallback YouTube Music.
- **Gapless Playback & Crossfade**: Transisi antar lagu tanpa jeda (0–12 detik).
- **Automix (DJ Transitions)**: Beat-matching & tempo-stretching otomatis.
- **Lirik Tersinkronisasi**: Animasi lirik dinamis bergaya Apple Music (word-by-word/syllable sync).
- **Bit-Perfect Output**: Output audio langsung ke USB DAC tanpa degradasi mixer Android.
- **Offline Mode**: Unduh lagu dengan metadata lengkap ke penyimpanan lokal.

    </td>
    <td width="50%" valign="top">

#### 🌟 UI & Integrasi Modern
- **Custom Multi-Theme Engine**: 6 palet warna eksklusif yang dapat diganti kapan saja.
- **Frosted-Glass Navigation & Mini Player**: Desain melayang (*floating bottom bar*) dengan blur dinamis (Haze).
- **Google Account Sync**: Masuk dengan akun Google untuk sinkronisasi playlist dan rekomendasi.
- **Spotify Integration**: Hubungkan akun Spotify untuk memutar playlist dan lagu yang disukai.
- **Discord Rich Presence**: Tampilkan lagu yang sedang diputar di status Discord.
- **Scrobbling**: Sinkronisasi ke Last.fm dan ListenBrainz.

    </td>
  </tr>
</table>

---

## 🛠️ Cara Membuka & Menjalankan di Android Studio

1. **Buka Proyek**:
   - Buka **Android Studio**.
   - Pilih **Open** dan arahkan ke folder:
     ```
     C:\Users\Rynz\.gemini\antigravity\scratch\RynzMusic
     ```
2. **Sinkronisasi Gradle**:
   - Android Studio akan secara otomatis mendeteksi Android SDK dan membuat file `local.properties`.
   - Klik tombol **Sync Project with Gradle Files** (ikon gajah).
3. **Pilih Run Configuration**:
   - Pilih modul target `:app`.
   - Pilih emulator Android atau perangkat HP fisik yang terhubung via USB Debugging.
4. **Jalankan Aplikasi**:
   - Klik tombol **Run** (`Shift + F10`) untuk mengompilasi dan memasang RynzMusic ke perangkat Anda.

---

## 📂 Struktur Proyek

```
RynzMusic/
├── app/                  # Modul utama aplikasi Android (UI, ViewModel, Services)
│   ├── src/main/java/com/music/bitchord/
│   │   ├── MainActivity.kt        # Entry point utama dengan inject tema RynzMusic
│   │   ├── ui/theme/Theme.kt      # Multi-Theme ColorScheme (6 palet dinamis)
│   │   ├── ui/screens/SettingsSheet.kt # Pengaturan Tema & Palet Warna
│   │   └── data/settings/AppSettings.kt # State persistent untuk AccentTheme
│   └── src/main/res/values/       # String resources & konfigurasi RynzMusic
├── shared/               # Modul logic shared (Extractor, Media3, Network)
├── sharedUi/             # Komponen UI bersama (Icons, Lirik, Artwork)
├── tv/                   # Modul Android TV
└── wearos/               # Modul Wear OS
```

---

<div align="center">
Dibuat dengan ❤️ untuk pengalaman mendengarkan musik terbaik di <b>RynzMusic</b>.
</div>
