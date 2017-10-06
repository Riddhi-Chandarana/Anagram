/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private static final HashMap<String, ArrayList<String>> LettertoWord = new HashMap<>();
    private static final HashSet<String> WordSet = new HashSet<>();
    private static final ArrayList<String> dic = new ArrayList<>();
    public static int wordLength =0;

    private static final HashMap< Integer,String> sizeToWords = new HashMap<>();
    private Random random = new Random();


    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            WordSet.add(word);
            String sort = SortLetters(word);
            if (LettertoWord.containsKey(sort)) {
                ArrayList<String> value = LettertoWord.get(sort);
                value.add(word);
                LettertoWord.put(sort, value);
            } else {
                ArrayList<String> value = new ArrayList<>();
                value.add(word);
                LettertoWord.put(sort, value);
            }
            wordLength=word.length();
            sizeToWords.put(wordLength,word);
            dic.add(word);
        }
    }

    public boolean isGoodWord(String word, String base) {
        if (word.contains(base))
            return false;
        if (WordSet.contains(word))
            return true;
        return false;

    }

    public List<String> getAnagrams(String targetWord) {
        //   ArrayList<String> result = new ArrayList<String>();
    /*   if(dictionary.containsValue(targetWord))
           result.add(targetWord);
*/
        String sort1 = SortLetters(targetWord);
   /* for(String word:dic)
    {
        if(word.length()==targetWord.length())
        {
            String sort=SortLetters(word);
           if(sort.equals(sort1))
                result.add(word);
        }
    }*/

        if (LettertoWord.containsKey(sort1))

            return LettertoWord.get(sort1);
        else
            return null;
        // return result;
    }

    public String SortLetters(String word) {
        char letter[] = word.toCharArray();
        Arrays.sort(letter);
        String neww = new String(letter);
        return neww;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for (char a = 'a'; a < 'z'; a++) {
            ArrayList<String> anagram = new ArrayList<>();
            String temp = word;
            temp += a;
            temp = SortLetters(temp);
            if (LettertoWord.containsKey(temp)) {
                anagram = LettertoWord.get(temp);
            }
            for (int i = 0; i < anagram.size(); i++) {
                if (isGoodWord(anagram.get(i), word)) {
                    result.add(anagram.get(i));
                }

            }

        }


        /*

        char intial='A';
        String anotherword=word;
        while(intial<='Z')
        {
            word=intial+anotherword;
            word=SortLetters(word);
            Log.d("getAna", word);
            if(LettertoWord.containsKey(word))
                result.addAll(LettertoWord.get(word));
            //word="";
            intial+=1;
        }
*/
        return result;
    }

    public String pickGoodStarterWord() {

        while (true) {

            int ran = random.nextInt() % 1000;
            if (ran >= 0 && ran < 1000) {
                String word = dic.get(ran);
                String key = SortLetters(word);
                if (LettertoWord.get(key).size() >= MIN_NUM_ANAGRAMS)
                    return dic.get(ran);
            }
        }
    }

  /* public String pickGoodStarterWord() {
       String tempStartWord = null;
       String sortTempStartWord = null;
       int numberOfAnagram = 0;
       ArrayList<String> StarterWords = new ArrayList<String>();

       do{
           StarterWords = sizeToWords.get(wordLength);
           tempStartWord = StarterWords.get(random.nextInt(StarterWords.size()));
           // sortTempStartWord = sortLetters(tempStartWord);
           numberOfAnagram = getAnagramsWithOneMoreLetter(tempStartWord).size();
            /*
            tempStartWord = wordList.get(random.nextInt(wordList.size()));
            sortTempStartWord = sortLetters(tempStartWord);
            numberOfAnagram = lettersToWord.get(sortTempStartWord).size();

       }while (numberOfAnagram <= MIN_NUM_ANAGRAMS);
       Log.d("test", "numberofAnagram: "+numberOfAnagram);
       if(wordLength <= MAX_WORD_LENGTH){
           wordLength++;
       }
       return tempStartWord;
      }*/
    }

