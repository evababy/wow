package sound;

import java.util.Vector;

import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.DataSink;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoDataSinkException;
import javax.media.NoProcessorException;
import javax.media.Processor;
import javax.media.control.StreamWriterControl;
import javax.media.format.AudioFormat;
import javax.media.protocol.DataSource;
import javax.media.protocol.FileTypeDescriptor;

public class CaptureDemo 
{

    /**
     * @param args
     */
    public static void main(String[] args) 
    {
        // TODO Auto-generated method stub
        CaptureDeviceInfo di = null;
        Processor p = null;
        StateHelper sh = null;
        Vector devList = CaptureDeviceManager.getDeviceList(new AudioFormat(javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED+"",44100,16,2));
        if(devList.size()>0)
        {
            di = (CaptureDeviceInfo)devList.firstElement();
            System.out.println(di.getName());
            System.out.println(di.toString());
        }
        else
            System.exit(-1);
        try
        {
            
            p = Manager.createProcessor(di.getLocator());             
            sh = new StateHelper(p);
        }
        catch(java.io.IOException ex)
        {
            System.exit(-1);
        }
        catch (NoProcessorException e) 
        {
            // TODO Auto-generated catch block
            System.exit(-1);
        }

        if(!sh.configure(10000))
        {
            System.exit(-1);
        }
        p.setContentDescriptor(new FileTypeDescriptor(FileTypeDescriptor.WAVE));
        
        if(!sh.realize(10000))
        {
            System.exit(-1);
        }
        
        DataSource source = p.getDataOutput();
        MediaLocator dest = new MediaLocator("file:///c:/foo.wav");
        DataSink fileWriter = null;
        try
        {
            fileWriter = Manager.createDataSink(source, dest);
            fileWriter.open();
        }
        catch (NoDataSinkException e) 
        {
            // TODO Auto-generated catch block
            System.exit(-1);
        }
        catch(java.io.IOException ex)
        {
            System.exit(-1);
        }
        catch(SecurityException ex)
        {
            System.exit(-1);
        }
        
        StreamWriterControl swc = (StreamWriterControl) p.getControl("javax.media.control.StreamWriterControl");
        if(swc!=null)
        {
            swc.setStreamSizeLimit(500000000);
        }
        try
        {
            fileWriter.start();
        }
        catch(java.io.IOException ex)
        {
            System.exit(-1);
        }
        
        sh.playToEndOfMedia(5000*1000);
        sh.close();
        fileWriter.close();
    }

}