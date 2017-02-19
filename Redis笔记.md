##Redis笔记
###Redis
- 高性能缓存与存储服务器
- 特性
	- 速度快
	- 持久化
	- 多种数据结构
	- 支持多种变成语言
	- 功能丰富
	- “简单”
	- 主从复制
	- 高可用、分布式
- 应用场景
	- 缓存系统（APP server -> redis -> mysql）
	- 计数器应用(记录一条微博的转发量、评论数等)
	- 消息队列系统
	- 排行榜应用（新书排行榜、帖子热度等）
	- 社交网络（feed流）
	- 实时系统

###安装
- 下载最新文档版安装包
- 解压缩
- 进入解压目录
- make 
- make install

启动

- redis-server configPath

验证启动

- ps -ef | grep redis
- netstat -antpl | grep redis
- redis-cli -h ip -p port ping

客户端
- Jedis

常用配置

###数据结构
####字符串
常用API
- get key 
- set key value
- del key
- incr key
- decr key
- incrby key k
- decrby key k
- set key value
- setnx key value
- set key value xx
- mget key1 key2 key3 ...
- mset key1 value1 key2 value2 key3 value3 ...
- getset key newvalue
- append key value
- strlen key(中文占两个字符)
- incrbyfloat key value
- getrange key start end
- setrange key index value

####哈希

field不能相同，value可以相同

重要API
- hget key field
- hset key field value
- hdel key field
- hexists key field
- hlen key
- hmget key field1 field2... fieldN
- hmset key field1 value1 field2 value2...fieldN valueN
- hgetall key(慎用)
- hvals key
- hkeys key
- hsetnx key field value
- hincrby key field intCounter
- hincrbyfloat key field floatCounter

####列表
特点
- 有序
- 可以重复
- 左右两边插入弹出

重要API
- rpush key value1 value2 ... valueN
- lpush key value1 value2 ... valueN
- linsert key before|after value newValue
- lpop key
- rpop key
- lrem key count value
- ltrim key start end
- lrange key start end
- lindex key index
- llen key
- lset key index newValue

应用
- 微博timeline
- blpop key timeout
- brpop key timeout

TIPS
- LPUSH + LPOP = Stack
- LPUSH + RPOP = Queue
- LPUSH + LTRIM = Capped Collection
- LPUSH + BRPOP = Message Queue

####集合

特性
- 无序
- 无重复

重要API
- sadd key element
- srem key element
- scard key
- sismember key value
- srandmember key count
- spop key
- smembers key(慎用)
- sdiff
- sinter
- sunion
- sdiff|sinter|sunion + store destKey 

TIPS
- SADD = Tagging
- SPOP/SRANDMEMBER=Random item
- SADD + SINTER = Social Graph

####有序集合
特点
- 无重复key
- 有序
- element + score

重要API
- zadd key score element
- zrem key element
- zscore key element
- zincrby key increScore element
- zcard key
- zrange key start end withscores
- zrangebyscore key minScore maxScore withscores
- zcount key minScore maxScore
- zremrangebyrank key start end
- zremrangebyscore key minScore maxScore

应用
- 排行榜

###Redis键值管理
- type key
	- string/hash/list/set/zset/none
- object encoding key #返回key的实际数据类型
	- sds/hash/linkedlist/ziplist/intset/skiplist
- del key
- exists key
- rename key newkey
- rename key newkey
- renamenx key newKey
- expire key seconds
- ttl key
- persist key
- keys [pattern]
- scan cursor [MATCH pattern][COUNT number]
- dbsize
- select
- flushdb
- flushall
- migrate
- dump
- pttl
- randomkey
- sort
- expireat

###持久化
方式
- 快照
- 写日志
####RDB
三种实现方式
- save
- bgsave
- 自动

文件策略
- 如果存在老的RDB文件，新替换老
- 复杂度：O(N)

配置

    dbfilename dump-${port}.rdb
    dir /bigdiskpath
    stop-writes-on-bgsave-error no
    rdbcompression yes
    rdbchecksum yes

####AOF
what aof?

AOF三种策略
- always
- everysec
- no

redis -> buffer -> -fsync- > disk

AOF重写作用
- 减少磁盘占用量
- 加速恢复速度
- ”不阻塞”读写

AOF重写实现两种方式
- bgrewriteaof（命令）
- 配置

配置

    appendonly yes
    appendfilename "appendonly-${port}.aof"
    appendfsync everysec
    no-appendfsync-on-rewrite yes
    auto-aof-rewrite-percentage 100
    auto-aof-rewrite-min-size 64mb

###发布与订阅
- publisher
- channel
- subscriber

API
- publish channel message
- subscribe [channel]
- unsubscribe [channel]

###Pipeline
N次网络时间+N次命令时间
vs
一次网络时间+N次命令时间

控制命令个数

###统计信息
- info #部分统计和状态
- info all #全部统计与状态
- info section 某一块的统计和状态

###主从复制



