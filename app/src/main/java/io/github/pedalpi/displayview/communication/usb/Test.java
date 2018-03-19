package io.github.pedalpi.displayview.communication.usb;

/*
public class UsbAccessoryActivity extends Activity {
    private FileInputStream mInput;
    private FileOutputStream mOutput;

    private void openAccessory() {
        UsbManager manager = UsbManager.getInstance(this);
        UsbAccessory accessory = UsbManager.getAccessory(getIntent());

        ParcelFileDescriptor fd = manager.openAccessory(accessory);

        if (fd != null) {
            mInput = new FileInputStream(fd);
            mOutput = new FileOutputStream(fd);
        } else {
            // Oh noes, the accessory didn’t open!
        }
    }
}
*/