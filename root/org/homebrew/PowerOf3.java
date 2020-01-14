package org.homebrew;

import java.io.PrintWriter;
import java.util.List;
import org.dvb.ui.DVBBufferedImage;
import org.havi.ui.HScene;

class PowerOf3
{
    public static void run(List messages, HScene scene)
    {
        long start_time = System.currentTimeMillis();
        messages.add("Creating array of DVBBufferedImage[32]... "); // cannot create more, probably a software limit
        scene.repaint();
        DVBBufferedImage[] arr = new DVBBufferedImage[32];
        for(int i = 0; i < 32; i++)
        {
            messages.set(messages.size()-1, "Creating array of DVBBufferedImage[32]... "+i+"/64");
            scene.repaint();
            arr[i] = new DVBBufferedImage(1000, 1000);
        }
        messages.set(messages.size()-1, "Creating array of DVBBufferedImage[32]... "+"done");
        messages.add("Creating arena (int[100000])... ");
        scene.repaint();
        int[] arena = new int[100000];
        messages.set(messages.size()-1, messages.get(messages.size()-1)+"done");
        messages.add("Performing calculations... ");
        scene.repaint();
        int x = 0xff000001;
        for(int i = 0; i < 32; i++)
            for(int j = 0; j < 10; j++)
            {
                for(int k = 0; k < 100000; k++)
                {
                    arena[k] = x;
                    x = (x * 3) | 0xff000000;
                }
                arr[i].setRGB(0, j*100, 1000, 100, arena, 0, 1000);
            }
        int y = 0xff000000;
        for(int i = 0; i < 32; i++)
            for(int j = 0; j < 10; j++)
            {
                arr[i].getRGB(0, j*100, 1000, 100, arena, 0, 1000);
                for(int k = 0; k < 100000; k++)
                {
                    y = (y + arena[k]) | 0xff000000;
                }
            }
        y = (y * 2) | 0xff000001;
        messages.set(messages.size()-1, messages.get(messages.size()-1)+"done");
        messages.add("Summed-up: "+x);
        messages.add("Directly calculated: "+y);
        scene.repaint();
        long end_time = System.currentTimeMillis();
        messages.add("");
        messages.add("Start time: "+start_time);
        messages.add("End time: "+end_time);
        messages.add("Calculation took "+(end_time-start_time)+" milliseconds.");
        scene.repaint();
    }
}
