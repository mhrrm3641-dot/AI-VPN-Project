import android.content.Intent
import android.net.VpnService
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Basit bir buton oluşturuyoruz
        val connectButton = Button(this).apply {
            text = "VPN'e Bağlan"
        }
        setContentView(connectButton)

        connectButton.setOnClickListener {
            startVpn()
        }
    }

    private fun startVpn() {
        val intent = VpnService.prepare(this)
        if (intent != null) {
            // Eğer sistem izin istememişse, izin penceresini açar
            startActivityForResult(intent, 0)
        } else {
            // İzin zaten varsa servisi başlatır
            onActivityResult(0, RESULT_OK, null)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val intent = Intent(this, AIVpnService::class.java)
            startService(intent)
        }
    }
}
