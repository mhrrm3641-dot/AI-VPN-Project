import android.content.Intent
import android.net.VpnService
import android.os.ParcelFileDescriptor
import android.util.Log

class AIVpnService : VpnService() {

    private var vpnInterface: ParcelFileDescriptor? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Servis başladığında bağlantı döngüsünü başlat
        establishVpn()
        return START_STICKY
    }

    private fun establishVpn() {
        try {
            val builder = Builder()
            
            // Konfigürasyon Ayarları
            builder.setSession("AI VPN Service")
                .addAddress("10.0.0.2", 32) // WireGuard iç IP örneği
                .addDnsServer("8.8.8.8")
                .addRoute("0.0.0.0", 0) // Tüm trafiği yönlendir
            
            // Mevcut bir arayüz varsa kapat
            vpnInterface?.close()
            
            // Yeni arayüzü oluştur
            vpnInterface = builder.establish()
            Log.d("AIVpnService", "VPN Arayüzü başarıyla kuruldu.")
            
        } catch (e: Exception) {
            Log.e("AIVpnService", "VPN kurulum hatası: ${e.localizedMessage}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        vpnInterface?.close()
        vpnInterface = null
    }
}
