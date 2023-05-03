package applications;

import applications.AtomStructure.AtomGame;
import applications.SocialNetworkCircle.SocialNetworkGame;
import applications.TrackGame.TrackGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class main {
    public void mains() throws IOException {
        System.out.println("选择你要使用的应用：");
        System.out.println("1.\tTrackGame");
        System.out.println("2.\tSocialNetworkCircle");
        System.out.println("3.\tAtomStructure");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine().trim();
        switch (input)
        {
            case"1":
                new TrackGame().gameMain();
                break;
            case"2":
                new AtomGame().gameMain();
                break;
            case"3":
                new SocialNetworkGame().gameMain();
                break;
        }
    }
    public static void main(String[] args) throws IOException {
        new main().mains();
    }
}
