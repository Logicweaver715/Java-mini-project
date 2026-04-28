package service;

import java.util.Random;
import model.PostureResult;

public class PostureAnalyzer {

    Random rand = new Random();

    public PostureResult analyze(String exercise){

        boolean ok = rand.nextBoolean();

        if(ok){
            return new PostureResult(true,"Correct Posture");
        }

        switch(exercise){

            case "Pushups":
                return new PostureResult(false,"Straighten your back");

            case "Squats":
                return new PostureResult(false,"Keep knees aligned");

            case "Plank":
                return new PostureResult(false,"Tighten core");

            default:
                return new PostureResult(false,"Adjust posture");
        }
    }
    public int score(boolean correct) {

        if (correct) {
            return 10;
        } else {
            return 5;
        }
    }
}

