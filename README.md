# Mu5ViewPager
根据图片高度动态变化的ViewPager
核心：根据每张图片的高度去动态计算更新viewpager的高度，核心原理详见Mu5ViewPager的OnPageChangeListener方法
# UI

![Mu5ViewPager](/gif/Mu5ViewPager.gif)

# How to use？
## Step1
 Add the JitPack repository to your build file
 
 	allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
  
## Step2
Add the dependency

	dependencies {
		compile 'com.github.JmStefanAndroid:Mu5ViewPager:1.2'
	}
 
 # 使用 [详见例子MainActivity](https://github.com/JmStefanAndroid/Mu5ViewPager/blob/master/app/src/main/java/me/stefan/mu5viewpager/MainActivity.java)
 
 ### 1.xml中
            <me.stefan.library.mu5viewpager.Mu5ViewPager
                android:id="@+id/viewpager"
                android:layout_width="match_parent"
                android:layout_height="wrap_content" />
                
 ### 2.实现接口 Mu5Interface
 
    public class 你的类 implements Mu5Interface{
    
        @Override //每页的变化 可用于显示页数 
        public void onIndexChange(int currentIndex) {
        }
        
        @Override  //完全自主的图片加载库，你可以选择任意你想用的三方库或者自己去实现 
        public void onLoadImage(final ImageView imageView, String url, final int position) {
                ...
                
                    mu5ViewPager.bindSource(loadedImage, position, imageView);//请务必在加载完成图片后调用bindSource
                
                ...
        }
    }
  
 ### 3.初始化
        
        mu5ViewPager = (Mu5ViewPager) findViewById(R.id.viewpager);
        
        mu5ViewPager.setData(datas, this);//datas支持绑定类型String[] 或者 List<String>
  
 # 注意 NOTICE!~
  1.若您想自定义OnPageChangeListener，请通过 setUserCustomPageChangeListener 方法来设置您的回调，否则将影响控件的使用
  

