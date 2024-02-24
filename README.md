# DataStructure_Team10

## 專案詳細細節請看：https://drive.google.com/file/d/1OVvI4Vpc30X35unP8qoOSD_SC-us-BMD/view?usp=sharing <br />

Presentation Slides: https://www.canva.com/design/DAFU1_E73BU/MJ-yX7awmpWVvnYS5pPeKQ/view?utm_content=DAFU1_E73BU&utm_campaign=designshare&utm_medium=link&utm_source=viewer

Demo Video: https://drive.google.com/drive/folders/1UDL15Iw0EItJe62iR7Oeu_1-OkCYleUk?usp=sharing

App（因檔案太大），請由此下載
App程式碼：https://drive.google.com/file/d/1FBI7snaNGGzOWz_fjYCykEhlOjnznVxd/view?usp=sharing
Apk檔案：https://github.com/RexRed6802/RamenSearcher/releases/download/1.0.0/app-debug.apk

以下內容皆節錄自上述連結

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
  When user search, and in googleQuery class , we will mandatorily and automatically add keyword like “拉麵” and “台北” to make the result more accurately.<br/>
  
c. The websites can be ranked: <br/>
  i. https://ramenroamer.com/ <br/>
  ii. https://www.ramenexplorer.com/<br/>
  iii. https://trade.1111.com.tw/web/%E6%8B%89%E9%BA%B5/<br/>
  ...etc<br/>
  
d. Formula:<br/>
  Score = count x weight<br/>


## 3. System Search Process and Design:
a. We start with the main function in the main class. First of all, we call the searchGoogle function with the keys provided by users. The searchGoogle will query Google and return a HashMap including url and website title. <br/>
   
b. Second of all, we call the buildTree function to build the tree, setting every url in HashMap as the tree root and store each root in our resultList to make sorting them more convenient soon. For each root node, we set its score by calling each root's node's page’s setScore function. Then we jump into the second loop to get those urls contained in every page and set their score as we did to the root node. In turn, finishing building trees. <br/>

c. By the way, when we call setScore function in every webpages, we will create a wordCounter object to count its score. Then we call setPostOrderScore for every root.<br/>

d. When finishing creating every tree in resultList. we can use the selectionSort function to sort them. <br/>
e. Publish our result to front-end (user) <br/>
f. Unified Modeling Language(UML) <br/>
![alt text](https://github.com/aposkend/DataStructure_Team10/blob/master/charts/UML_DS.1.jpeg)















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
