# DataStructure_Team10

## introduction - Motivation: <br />
a. Topic: The Best Search Engine for RAMEN Lovers <br />
b. Motivation: Recently, collecting different kinds of Ramen has become a trend in Taiwan. Those comments left on social media let more and more “novices” get to know this culture. However, we think some information is not accurate at all. Therefore, we wants to make a search engine for those RAMEN mania to get the rapidly and accurately RAMEN restaurants with high quality. <br />


## Searching Methodology <br />

a. Keywords:
  
KEYWORD | WEIGHT <br/>
  拉麵   |   500 <br/>
  豚骨   |   300 <br/>
  叉燒   |   300 <br/>
  雞白湯 |   200 <br/>
  醬油   |   200 <br/>
  台北   |   200 <br/>
  排隊   |    50 <br/>
  好吃   |    30 <br/>
  湯底   |    30<br/> 
  激辛   |    30<br/> 
  日本   |    30<br/>
  日式   |    30<br/>
  美食   |    30<br/>
  湯     |     5<br/>
  麵     |     5<br/>
  
b. Compulsory keyword:<br/>
  When user search, and in googleQuery class , we will mandatorily and automatically add keyword like “拉麵” and “台北” to make the result more accurately.
c. The websites can be ranked:<br/>
  i. https://ramenroamer.com/
  ii. https://www.ramenexplorer.com/
  iii. https://trade.1111.com.tw/web/%E6%8B%89%E9%BA%B5/
d. Formula:<br/>
  Score = count x weight















Introduction: https://www.canva.com/design/DAFU1_E73BU/MJ-yX7awmpWVvnYS5pPeKQ/view?utm_content=DAFU1_E73BU&utm_campaign=designshare&utm_medium=link&utm_source=viewer

Demo影片: https://drive.google.com/drive/folders/1UDL15Iw0EItJe62iR7Oeu_1-OkCYleUk?usp=sharing

App（因檔案太大），請由此下載

App程式碼：https://drive.google.com/file/d/1FBI7snaNGGzOWz_fjYCykEhlOjnznVxd/view?usp=sharing

Apk檔案：https://github.com/RexRed6802/RamenSearcher/releases/download/1.0.0/app-debug.apk

This is a project for Data Structure course.

[2022.12.16]
1. finish finding relative words
2. finish front-end design
3. The project is done.... maybe

[2022.11.29]
1. front-end design is start
![image](https://github.com/RexRed6802/DataStructure_Team10/blob/master/img/截圖%202022-11-29%20下午12.24.55.png)

[2022.11.26]
1. Implement multithreading in our project and indeed speed up the crawling time.(just 10 seconds to build and search 40 web trees)

[2022.11.20]
1. solve some of bugs like url not found or crawl the websites that with no content
2. concise the code
3. change our topic to ramen

[2022.10.22]
1. finish basic searching enginee but still need to optimize and beautify
![image](https://github.com/RexRed6802/DataStructure_Team10/blob/master/img/截圖%202022-10-22%20下午9.06.51.png)
