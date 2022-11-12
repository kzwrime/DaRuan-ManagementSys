# vuepro

## API parameters：

### 1.loginPage

>   post: ->email,password
>
>   ​		<-checkResult,user_identity

### 2.UserPage

#### 2.1 getRemoteData

>   get: ->category,pageSize,pageNum
>
>    ​		<- Data in json

#### 2.2keywordSearch

>   get:-> keyword from user input (可能需要模糊查询)

#### 2.3 handleCurrentChange

>   get: ->same as getRemoteData
>
>   ​		<- Data in json

#### 2.4 reserveBook

>   post: -> email,book id

#### 2.5 book_back

>   post: ->user_email book_id book_name
