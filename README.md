# AndroidSkin
### 一个使用成本极低的换肤框架 

- 支持插件式换肤
- 支持继承和非继承式换肤，无需对base层进行代码侵入
- 支持自定义View
- 支持多种加载类型，Assets，Storage ,Zip 等
- 内部直接进行换肤，无需重启

### 目前支持的内置 View  👨‍🔧‍

___



|                    | backaground | 字体颜色 | 字体大小 | 图片 | hint |
| ------------------ | :---------: | :------: | :------: | :--: | :--: |
| View               |      ✅      |          |          |      |      |
| AppCompatTextView  |      ✅      |    ✅     |    ✅     |      |      |
| AppCompatButton    |      ✅      |    ✅     |    ✅     |      |      |
| AppCompatImageView |      ✅      |          |          |  ✅   |      |
| ConstraintLayout   |      ✅      |          |          |      |      |
| LinearLayoutCompat |      ✅      |          |          |      |      |
| NestedScrollView   |      ✅      |          |          |      |      |
| FrameLayout        |      ✅      |          |          |      |      |
| RelativeLayout     |      ✅      |          |          |      |      |
| AppCompatEditText  |      ✅      |    ✅     |    ✅     |      |  ✅   |

> 其他的陆续支持中

### 使用方式

#### 初始化

```
SkinManager.init(this)
    .addInflaters(SkinAppCompatViewInflater()) 
    .setAutoLoadSkin(true)
    .build()
```

- SkinAppCompatViewInflater

  框架内部支持换肤的 View ， 如果需要对自定义 View 进行换肤，可实现 SkinLayoutInflater 接口，具体可参考 `SkinAppCompatViewInflater` 内部实现。

  最后将自定义 Inflater 在初始化时添加即可

- setAutoLoadSkin

  是否使用非继承式的方式实现换肤,默认 true

#### 加载皮肤

- None

  加载默认皮肤，即不使用皮肤

- Assets

  将皮肤文件放在 assets 目录下，传入即可

  ```kotlin
  /**
       * 加载资源文件夹下的皮肤
       * @param name 资源文件名
       * @param skinLoadListener 回调
       * @param isRepeat false 表示要加载的 skin 和当前使用的相同时不重复加载
       */
      fun loadAssetsSkin(
          name: String,
          skinLoadListener: SkinLoadListener? = null,
          isRepeat: Boolean = false,
      )
  ```

- Storage

  将皮肤文件下载到沙箱中，传入绝对路径即可

  ```kotlin
  /**
   * 加载内部存储下的皮肤，必须是沙箱路径
   * @param path skin 绝对路径
   * @param skinLoadListener 回调
   * @param isRepeat false 表示要加载的 skin 和当前使用的相同时不重复加载
   */
  fun loadStorageSkin(
      path: String,
      skinLoadListener: SkinLoadListener? = null,
      isRepeat: Boolean = false
  )
  ```

- Zip

  将下载好的 zip 的绝对路径传入即可，如果是加密的Zip，则需要传入密码

  ```kotlin
  /**
   * 加载内部存储下的 zpi 皮肤文件，必须是沙箱路径
   * @param path skin 绝对路径
   * @param password zip 文件密码，没有可不传
   * @param skinLoadListener 回调
   * @param isRepeat false 表示要加载的 skin 和当前使用的相同时不重复加载
   */
  fun loadZipSkin(
      path: String,
      password: String? = null,
      skinLoadListener: SkinLoadListener? = null,
      isRepeat: Boolean = false
  )
  ```



### 需要注意的点

- 创建皮肤

  新建一个项目，将需要换肤的资源放在此项目中，然后修改资源值即可。

  > 注意，资源名称必须和主项目资源名称一致，后缀必须是 .skin 。压缩文件的原文件也是如此

- 更新皮肤资源

  如果当前使用的皮肤 `red.skin` 内部的资源发生了改变，则需要修改皮肤名称，例如修改为 `red_1.skin` 。这样做的原因是本框架中对于从 `Assets` 以及 `Zip` 加载类型的有缓存策略，如果不修改名称，默认就会加载到缓存皮肤中。

