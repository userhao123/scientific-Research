package com.hao.scientificresearch.textClassifier;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;


/**
 * 实现描述：朴素贝叶斯分类
 *
 * @author hao
 * @version v1.0.0
 *          1.先验概率P(y)= 类y下单词总数/整个训练样本的单词总数
 *          2.类条件概率P(xi|y)=(类y下单词xi在各个文档中出现过的次数之和+1)/(类y下单词总数+向量的维度)
 *          3.P(y|xi)=P(y)*P(xi|y)/P(xi)等价于P(y)*P(xi|y)
 * @since 2021-04-05
 */
public class NBClassifier {

    private Map<String, Double> wordWeightMap;

    private Map<String, Integer> yesDocMap;
    private Map<String, Integer> noDocMap;

    private double yesPrioriProb;
    private double noPrioriProb;

    private Long wordDifCount;

    private Long yesClassWordCount;
    private Long noClassWordCount;
    //各分类词频map
    private Map<String, Integer> sportMap;
    private Map<String, Integer> itMap;
    private Map<String, Integer> financeMap;
    private Map<String, Integer> otherMap;
    //各分类权重
    private double sportPrioriProb;
    private double itPrioriProb;
    private double financePrioriProb;
    private double otherPrioriProb;
    //各分类词频总数
    private Long sportClassWordCount;
    private Long itClassWordCount;
    private Long financeClassWordCount;
    private Long otherClassWordCount;



    public NBClassifier(Map<String, Integer> yesDocMap, Map<String, Integer> noDocMap, Map<String, Double> wordWeightMap) {
        this.wordWeightMap = wordWeightMap;

        this.wordDifCount = (long) this.wordWeightMap.size();

        this.yesDocMap = yesDocMap;
        this.noDocMap = noDocMap;

        yesPrioriProb = 1.0 * yesDocMap.size() / (yesDocMap.size() + noDocMap.size());
        noPrioriProb = 1.0 * noDocMap.size() / (yesDocMap.size() + noDocMap.size());

        this.yesClassWordCount = getWordCount(yesDocMap);
        this.noClassWordCount = getWordCount(noDocMap);
    }

    public NBClassifier(Map<String, Integer> sportDocMap, Map<String, Integer> itDocMap,
                        Map<String, Integer> financeDocMap,
                        Map<String, Integer> otherDocMap,Map<String, Double> wordWeightMap) {
        this.wordWeightMap = wordWeightMap;

        this.wordDifCount = (long) this.wordWeightMap.size();
        //初始化各分类词频map（关键词为key,次数为value）
        this.sportMap = sportDocMap;
        this.itMap = itDocMap;
        this.financeMap = financeDocMap;
        this.otherMap = otherDocMap;
        //初始化各分类的权重
        sportPrioriProb = 1.0 * sportMap.size() / (sportMap.size()+itMap.size()+financeMap.size()+otherMap.size());
        itPrioriProb = 1.0 * itMap.size() / (sportMap.size()+itMap.size()+financeMap.size()+otherMap.size());
        financePrioriProb = 1.0 * financeMap.size() / (sportMap.size()+itMap.size()+financeMap.size()+otherMap.size());
        otherPrioriProb = 1.0 * otherMap.size() / (sportMap.size()+itMap.size()+financeMap.size()+otherMap.size());
        //初始化各个分类下的词频总数
        this.sportClassWordCount = getWordCount(sportMap);
        this.itClassWordCount = getWordCount(itMap);
        this.financeClassWordCount = getWordCount(financeMap);
        this.otherClassWordCount = getWordCount(otherMap);

    }



    /**
     * 增量训练
     *
     * @param docMap 　待加入训练集的数据
     * @param isbYes 　对应标签
     */
    public void train(Map<String, Integer> docMap, boolean isbYes) {
        if (isbYes) {
            this.yesDocMap = mergeDocMap(this.yesDocMap, docMap);
        } else {
            this.noDocMap = mergeDocMap(this.noDocMap, docMap);
        }

        yesPrioriProb = 1.0 * yesDocMap.size() / (yesDocMap.size() + noDocMap.size());
        noPrioriProb = 1.0 * noDocMap.size() / (yesDocMap.size() + noDocMap.size());

        this.yesClassWordCount = getWordCount(yesDocMap);
        this.noClassWordCount = getWordCount(noDocMap);
    }


    /**
     * 分类文档
     *
     * @param wordFreMap 　待分类文档对应的单词－频数映射
     * @return 各分类下的概率列表
     */
    public double[] classify(Map<String, Integer> wordFreMap) {
        double[][] conditionalProb = new double[4][wordFreMap.size()];
        conditionalProb[0] = getProbMatrix(sportMap, sportClassWordCount, wordFreMap);
        conditionalProb[1] = getProbMatrix(itMap, itClassWordCount, wordFreMap);
        conditionalProb[2] = getProbMatrix(financeMap, financeClassWordCount, wordFreMap);
        conditionalProb[3] = getProbMatrix(otherMap, otherClassWordCount, wordFreMap);

        double[] classProb = {sportPrioriProb, itPrioriProb, financePrioriProb, otherPrioriProb};
        for (int i = 0; i < classProb.length; ++i) {
            for (int j = 0; j < wordFreMap.size(); ++j) {
                classProb[i] *= conditionalProb[i][j];
            }
        }
        System.out.println("原概率列表:"+ Arrays.toString(classProb));
        return classProb;
    }

    /**
     * 概率归一化
     *
     * @param classProb 　原概率列表
     * @return
     */
    public double[] normalized(double[] classProb) {
        double[] classProbAfterNor = new double[classProb.length];
        double sum = 0.0;
        for (double v : classProb) {
            sum += v;
        }
        for (int i = 0; i < classProb.length; ++i) {
            classProbAfterNor[i] = classProb[i] / sum;
        }
        System.out.println("归一化后的概率列表:"+ Arrays.toString(classProbAfterNor));
        return classProbAfterNor;
    }

    /**
     * 计算给定分类属性取值下每个特征属性不同取值的条件概率矩阵
     *
     * @param docMap         　给定分类属性取值对应的单词－频数映射
     * @param classWordCount 给定分类属性取值的单词总频数
     * @param wordFreMap     　待分类文档对应的单词－频数映射
     * @return 待分类文档的一类概率数组
     */
    private double[] getProbMatrix(Map<String, Integer> docMap, Long classWordCount, Map<String, Integer> wordFreMap) {
        double[] probMatrixPerClass = new double[wordFreMap.size()];
        int index = 0;
        for (Map.Entry<String, Integer> stringIntegerEntry : wordFreMap.entrySet()) {
            Map.Entry entry = stringIntegerEntry;
            String key = (String) entry.getKey();
            Long tmpCount = 0L;
            Double weight = 1.0;
            if (docMap.containsKey(key)) {
                tmpCount = (long) docMap.get(key);
            }
            if (this.wordWeightMap.containsKey(key)) {
                weight = this.wordWeightMap.get(key);
            }
            //TF-IDF的思想计算权重比率
            probMatrixPerClass[index++] = 1.0 * (tmpCount + 1) * weight / (classWordCount + this.wordDifCount);
        }
        return probMatrixPerClass;
    }

    /**
     * 计算docMap中所有单词频数和
     *
     * @param docMap 　单词－频数映射
     * @return  所有单词频数和
     */
    private Long getWordCount(Map<String, Integer> docMap) {
        Long totalFrequency = 0L;
        for (Map.Entry<String, Integer> stringIntegerEntry : docMap.entrySet()) {
            Map.Entry entry = stringIntegerEntry;
            totalFrequency += (Integer) entry.getValue();
        }
        return totalFrequency;
    }

    /**
     * 将文档合并到训练文档集
     *
     * @param allDocMap 　训练文档集
     * @param docMap    　待增量添加文档
     * @return  合并后的训练文档集
     */
    private Map<String, Integer> mergeDocMap(Map<String, Integer> allDocMap, Map<String, Integer> docMap) {
        for (Map.Entry<String, Integer> stringIntegerEntry : docMap.entrySet()) {
            Map.Entry entry = stringIntegerEntry;
            String key = (String) entry.getKey();
            Integer value = (Integer) entry.getValue();
            if (allDocMap.containsKey(key)) {
                allDocMap.put(key, allDocMap.get(key) + value);
            } else {
                allDocMap.put(key, value);
            }
        }
        return allDocMap;
    }
}
