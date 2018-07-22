package io.github.pedalpi.displayview.communication.usb;


import android.app.Activity;
import android.content.Context;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class UsbAccessoryActivity extends Activity {
    private FileInputStream inputStream;
    private FileOutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("ASD", "aPARENTEMENTE funcionou");
    }

    private void openAccessory() {
        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        UsbAccessory accessory = getIntent().getParcelableExtra(UsbManager.EXTRA_ACCESSORY);

        Log.d("Acessory", "openAccessory: " + accessory);
        ParcelFileDescriptor mFileDescriptor = manager.openAccessory(accessory);

        if (mFileDescriptor != null) {
            FileDescriptor fd = mFileDescriptor.getFileDescriptor();
            inputStream = new FileInputStream(fd);
            outputStream = new FileOutputStream(fd);
            //Thread thread = new Thread(null, this, "AccessoryThread");
            //thread.start();
        }
    }
}
