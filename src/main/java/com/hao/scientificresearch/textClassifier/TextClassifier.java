package com.hao.scientificresearch.textClassifier;

import com.google.common.collect.Maps;
import org.ansj.util.FilterModifWord;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 实现描述：分类入口
 *
 * @author hao
 * @since 2021-04-05
 */
public class TextClassifier {

//    private static String YES_CLASS_INPUT_DIR = MainApp.class.getResource("/text/yes/").getPath();
//
//    private static String NO_CLASS_INPUT_DIR = MainApp.class.getResource("/text/no/").getPath();

    private static String IT = TextClassifier.class.getResource("/text/it/").getPath();
    private static String FINANCE = TextClassifier.class.getResource("/text/finance/").getPath();
    private static String SPORT = TextClassifier.class.getResource("/text/sport/").getPath();
    private static String OTHER = TextClassifier.class.getResource("/text/other/").getPath();

    private static String STOP_WORD_FILE = TextClassifier.class.getResource("/text/stop_word.txt").getPath();

    private static String INPUT_FILE1 = TextClassifier.class.getResource("/text/test.txt").getPath();

    public static void main(String[] args) throws IOException {
        //过滤停用词
        HashMap<String, String> stopWordMap = AnsjSpliter.filter(new File(STOP_WORD_FILE));
        FilterModifWord.setUpdateDic(stopWordMap);

        //分词关键词统计
//        Map<String, Integer> yesDocMap = AnsjSpliter.process(new File(YES_CLASS_INPUT_DIR));
//        System.out.println("yesDocMap" + yesDocMap);
//
//        Map<String, Integer> noDocMap = AnsjSpliter.process(new File(NO_CLASS_INPUT_DIR));
//        System.out.println("noDocMap" + noDocMap);

        //词频统计
        Map<String, Integer> itMap = AnsjSpliter.process(new File(IT));
        System.out.println("itMap"+itMap);
        Map<String, Integer> financeMap = AnsjSpliter.process(new File(FINANCE));
        System.out.println("financeMap"+financeMap);
        Map<String, Integer> sportMap = AnsjSpliter.process(new File(SPORT));
        System.out.println("sportMap"+sportMap);
        Map<String, Integer> otherMap = AnsjSpliter.process(new File(OTHER));
        System.out.println("otherMap"+otherMap);

//        Map<String, Integer> testDocMap = AnsjSpliter.process(new File(INPUT_FILE1));
//        System.out.println("testDocMap" + testDocMap);

        Map<String, Integer> testDocMap = AnsjSpliter.process("金融管理系统的开发与设计实现");
        System.out.println("待分类map:"+testDocMap);

        Map<String, Double> wordWeightMap = Maps.newHashMap();
        wordWeightMap.put("体育",15.0);
        wordWeightMap.put("互联网",11.6);
        wordWeightMap.put("财经",11.6);
        wordWeightMap.put("金融",11.6);

        //朴素贝叶斯分类
//        NBClassifier classifier = new NBClassifier(yesDocMap, noDocMap, wordWeightMap);
        NBClassifier classifier = new NBClassifier(sportMap, itMap, financeMap, otherMap, wordWeightMap);
        double[] classProb = classifier.classify(testDocMap);

        classProb = classifier.normalized(classProb);
        printClass(classProb);

//        classifier.train(testDocMap, false);
//        classProb = classifier.classify(testDocMap);
//
//        classProb = classifier.normalized(classProb);
//        print(classProb);

    }

    public static String classifier(String text) throws IOException {
        //过滤停用词
        HashMap<String, String> stopWordMap = AnsjSpliter.filter(new File(STOP_WORD_FILE));
        FilterModifWord.setUpdateDic(stopWordMap);

        //词频统计
        Map<String, Integer> itMap = AnsjSpliter.process(new File(IT));
        System.out.println("itMap"+itMap);
        Map<String, Integer> financeMap = AnsjSpliter.process(new File(FINANCE));
        System.out.println("financeMap"+financeMap);
        Map<String, Integer> sportMap = AnsjSpliter.process(new File(SPORT));
        System.out.println("sportMap"+sportMap);
        Map<String, Integer> otherMap = AnsjSpliter.process(new File(OTHER));
        System.out.println("otherMap"+otherMap);


        Map<String, Integer> testDocMap = AnsjSpliter.process(text);
        System.out.println("待分类map:"+testDocMap);

        Map<String, Double> wordWeightMap = Maps.newHashMap();
        wordWeightMap.put("体育",5.0);
        wordWeightMap.put("互联网",1.6);
        wordWeightMap.put("财经",1.6);
        wordWeightMap.put("金融",1.6);

        //朴素贝叶斯分类
        NBClassifier classifier = new NBClassifier(sportMap, itMap, financeMap, otherMap, wordWeightMap);
        double[] classProb = classifier.classify(testDocMap);

        classProb = classifier.normalized(classProb);
         return printClass(classProb);
    }


    private static String printClass(double[] classProb) {

        ArrayList<String> list = new ArrayList<>(4);
        list.add("体育");
        list.add("互联网");
        list.add("财经");
        list.add("其他");
        System.out.println("sport 概率" + classProb[0]);
        System.out.println("it 概率" + classProb[1]);
        System.out.println("finance 概率" + classProb[2]);
        System.out.println("other 概率" + classProb[3]);
        double max = 0.0;
        int index = 0;
        for(int i=0;i<classProb.length;i++){
            if(classProb[i] > max){
                max = classProb[i];
                index = i;
            }
        }
        String s = list.get(index);
        System.out.println("分类为"+s);
        return s;

    }

}
