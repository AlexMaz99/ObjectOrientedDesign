package pl.edu.agh.to.lab4.suspects;

import pl.edu.agh.to.lab4.suspects.Suspect;

import java.util.Calendar;

public class Prisoner extends Suspect {
    private final int judgementYear;

    private final int sentenceDuration;

    private final String personalId;

    public Prisoner(String name, String surname, String personalId, int judgementYear, int sentenceDuration, int age) {
        super(name, surname, age);
        this.personalId = personalId;
        this.judgementYear = judgementYear;
        this.sentenceDuration = sentenceDuration;
    }

    public boolean isJailedNow() {
        return judgementYear + sentenceDuration >= getCurrentYear();
    }

    public int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public String getPersonalId() {
        return personalId;
    }

    public int getJudgementYear() {
        return judgementYear;
    }

    public int getSentenceDuration() {
        return sentenceDuration;
    }

    @Override
    public boolean canBeAccused() {
        return !isJailedNow();
    }
}
