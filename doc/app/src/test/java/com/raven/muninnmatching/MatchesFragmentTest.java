package com.raven.muninnmatching;

import com.raven.muninnmatching.Models.Matches.MatchesFragment;

import org.junit.Test;
import static org.junit.Assert.*;

public class MatchesFragmentTest {
    public static final MatchesFragment mf = new MatchesFragment();
    @Test
    public void MatchesFragmentTest_Matches(){
        int tag1 = 0;
        int tag1a = 1;
        int tag1b = 2;
        int tag2a = 3;

        double expResult1 = 0.0;
        double expResult2 = 1/3;
        double expResult3 = 2/3;
        double expResult4 = 3/3;
        double expResult5 = 1/1;

        //0/0
        double result1 = mf.matchPercent(tag1,tag1);
        System.out.println(expResult1 + result1);
        //1/3
        double result2 = mf.matchPercent(tag1a,tag2a);
        System.out.println(result2);
        //3/2
        double result3 = mf.matchPercent(tag2a, tag1b);
        System.out.println(result3);
        //3/3
        double result4 = mf.matchPercent(tag2a,tag2a);
        System.out.println(result4);
        //1/1
        double result5 = mf.matchPercent(tag1a,tag1a);
        System.out.println(result5);


    }
}
