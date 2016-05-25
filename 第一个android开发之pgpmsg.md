# 第一个android开发之pgpmsg（补充）

首先，了解了android项目的大体脉络：

Main activity:java文件，可以说是主进程，其中onCreate方法（java中都叫方法）可以说是对一切的初始化（但是当程序由后台切换到前台时并没有再次执行onCreate方法，如何激活想要的流程还需后续了解）。

下面用英语写吧，切换过去切换过来好烦

the problem of renew the content of clipboard had done with awesome myself

First lets see a picture:

 ![屏幕快照 2016-05-13 下午4.29.51](/Users/arics/Desktop/屏幕快照 2016-05-13 下午4.29.51.png)





As we can see, when activity move to the background and move to front again,the activity will run onRestart() which can remote onStart(), apart from that, the onCreate also would promote the method of onStart(), so I can add the judgement of whether the content of clipboard already was renewed.  

### Methods of Mainactivity

onCreate is called when the activity is created as same as innitiated.

setContentView(R.layout.Layoutname) make the layout of Mainactivity

### Other aspects of android

#### Clipboard

System clipboard:Android make an object called ClipboardManager to hold the text(pic and other contains have no idea to manage)which the global clipped.

#### Encryption and Decryption

1 Hash method

java.security
public abstract class MessageDigest
extends java.security.MessageDigestSpi
Uses a one-way hash function to turn an arbitrary number of bytes into a fixed-length byte sequence. The original arbitrary-length sequence is the message, and the fixed-length byte sequence is the digest or message digest.

2 asymmetric encryption:RSA

com.example.android.encryptmsg
public class RSAUtils
extends Object

usually the key of encryption and decryption needs to be encode with BASE64

```
String enHash=encoder.encode(RSAUtils.encryptByPrivateKey(hash.getBytes(),DEFAULT_PRIVATE_KEY));
```

3 symmetric Encryption AES

the AES class of this project can be reused



