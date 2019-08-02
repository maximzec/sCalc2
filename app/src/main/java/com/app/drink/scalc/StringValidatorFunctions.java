package com.app.drink.scalc;

import java.util.HashSet;

class StringValidator {
    static HashSet<Character> actionTokens;
    static HashSet<Character> bracketTokens;
    Character dot ;


    StringValidator()
    {
        actionTokens = new HashSet<>();
        bracketTokens = new HashSet<>();
        dot = ',';
        actionTokens.add('+');
        actionTokens.add('÷');
        actionTokens.add('×');
        actionTokens.add('−');
        bracketTokens.add('(');
        bracketTokens.add(')');
    }



    public String checkStringByActions(String s)
    {
        if(s.length() >= 2)
        {
            if(actionTokens.contains(s.charAt(s.length()-1)) && actionTokens.contains(s.charAt(s.length()-2)))
            {
                s = s.substring(0, s.length() - 2 ) + s.substring(s.length()-1);
            }
        }
        return s;
    }


    public String checkStringByDot(String s)
    {
        if(s.length()>=2) {
            if (s.charAt(s.length() - 1) == ',' && s.charAt(s.length() - 2) == ',') {
                s = s.substring(0, s.length() - 2 ) + s.substring(s.length()-1);
            }
        }
        return s;
    }

    public boolean isRightBracketAvaible(String s)
    {
        return characterCount('(', s) - characterCount(')', s) == 1;
    }

    public boolean isLeftBracketAvaible(String s)
    {
        return characterCount('(' , s) == characterCount(')' ,s);
    }



    public boolean isAction(CharSequence c)
    {
        return actionTokens.contains(c);
    }

    public boolean isBracket(CharSequence c)
    {
        return bracketTokens.contains(c);
    }

    private int characterCount(char c , String s)
    {
        int count = 0;
        for(int i = 0 ; i < s.length() ; i++)
        {
            if(s.charAt(i) == c) count++;
        }

        return count;
    }


    public  HashSet<Character> getActionTokens() {
        return actionTokens;
    }

    public HashSet<Character> getBracketTokens()
    {
        return bracketTokens;
    }
}
