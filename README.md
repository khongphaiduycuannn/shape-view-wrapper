# Shape View Wrapper

Thay thế việc tạo drawable XML truyền thống bằng các thuộc tính view trực tiếp.

## Mục lục

- [Cài đặt](#cài-đặt)
- [Shape View (Base)](#shape-view-base)
- [Ripple Shape View (Extended)](#ripple-shape-view-extended)
- [Utils & Extensions](#utils--extensions)

## Cài đặt
```gradle
def version = "1.0.1"
implementation "com.github.khongphaiduycuannn:shape-view-wrapper:$version"
```

---

# Shape View (Base)

## Tổng quan

Shape View Wrapper cung cấp các custom view hỗ trợ styling trực tiếp thông qua XML attributes, loại bỏ nhu cầu tạo các file drawable resource riêng biệt.

## Các View được hỗ trợ

Thêm tiền tố `Shape` vào các view Android chuẩn:
- `ShapeView`
- `ShapeTextView`
- `ShapeButton`
- `ShapeImageView`
- `ShapeEditText`
- `ShapeConstraintLayout`
- `ShapeLinearLayout`
- `ShapeFrameLayout`
- `ShapeRelativeLayout`
- Và hầu hết các View/ViewGroup phổ biến khác

## Danh sách thuộc tính

### Bo góc & Hình dạng

| Thuộc tính | Kiểu | Mô tả |
|-----------|------|-------|
| `app:shape_radius` | dimension | Bo tròn tất cả 4 góc với cùng một giá trị |
| `app:shape_radiusInTopStart` | dimension | Bo tròn góc trên bên trái |
| `app:shape_radiusInTopEnd` | dimension | Bo tròn góc trên bên phải |
| `app:shape_radiusInBottomStart` | dimension | Bo tròn góc dưới bên trái |
| `app:shape_radiusInBottomEnd` | dimension | Bo tròn góc dưới bên phải |

### Màu nền đơn sắc

| Thuộc tính | Kiểu | Mô tả |
|-----------|------|-------|
| `app:shape_solidColor` | color | Màu nền đơn sắc |

### Gradient nền

| Thuộc tính | Kiểu | Mô tả |
|-----------|------|-------|
| `app:shape_solidGradientStartColor` | color | Màu bắt đầu của gradient nền |
| `app:shape_solidGradientCenterColor` | color | Màu trung tâm của gradient nền (tùy chọn) |
| `app:shape_solidGradientEndColor` | color | Màu kết thúc của gradient nền |
| `app:shape_solidGradientOrientation` | enum | Hướng của gradient: `startToEnd`, `endToStart`, `topToBottom`, `bottomToTop`, `topStartToBottomEnd`, `bottomStartToTopEnd`,... |

### Viền (Border)

| Thuộc tính | Kiểu | Mô tả |
|-----------|------|-------|
| `app:shape_strokeSize` | dimension | Độ dày của viền |
| `app:shape_strokeColor` | color | Màu viền đơn sắc |
| `app:shape_strokeGradientStartColor` | color | Màu bắt đầu của gradient viền |
| `app:shape_strokeGradientCenterColor` | color | Màu trung tâm của gradient viền (tùy chọn) |
| `app:shape_strokeGradientEndColor` | color | Màu kết thúc của gradient viền |
| `app:shape_strokeGradientOrientation` | enum | Hướng của gradient viền |

### Đổ bóng

| Thuộc tính | Kiểu | Mô tả |
|-----------|------|-------|
| `app:shape_shadowSize` | dimension | Độ mờ/blur của bóng đổ |
| `app:shape_shadowColor` | color | Màu của bóng đổ |
| `app:shape_shadowOffsetX` | dimension | Độ lệch ngang của bóng (âm = trái, dương = phải) |
| `app:shape_shadowOffsetY` | dimension | Độ lệch dọc của bóng (âm = lên, dương = xuống) |

### Định dạng chữ

*Áp dụng đối với các View chứa text*

| Thuộc tính | Kiểu | Mô tả |
|-----------|------|-------|
| `app:shape_textColor` | color | Màu chữ đơn sắc |
| `app:shape_textStartColor` | color | Màu bắt đầu của gradient text |
| `app:shape_textCenterColor` | color | Màu trung tâm của gradient text (tùy chọn) |
| `app:shape_textEndColor` | color | Màu kết thúc của gradient text |
| `app:shape_textGradientOrientation` | enum | Hướng gradient text: `horizontal`, `vertical` |
| `app:shape_textStrokeColor` | color | Màu viền chữ |
| `app:shape_textStrokeSize` | dimension | Độ dày viền chữ |

## Ví dụ XML
```xml
<com.hjq.shape.view.ShapeButton
    android:id="@+id/shapeButton"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:gravity="center"
    android:padding="16dp"
    android:text="Shape Button"

    app:shape_radius="12dp"
    app:shape_solidColor="#6200EE"
    app:shape_strokeSize="2dp"
    app:shape_strokeColor="#FFFFFF"
    app:shape_shadowSize="8dp"
    app:shape_shadowColor="#80000000"
    app:shape_shadowOffsetY="4dp" />
```

Tham khảo ví dụ đầy đủ trong `activity_main.xml`

## Sử dụng trong code

Đối với tất cả các ShapeView, chứa bên trong một inner background builder.

Đối với các View chứa text, có thêm một inner text builder.

Config các builder này để áp dụng hiệu ứng.

**Ví dụ với Background Builder:**
```kotlin
binding.shapeButton.shapeDrawableBuilder
    .setSolidColor(R.color.purple_200)
    .setRadius(15f)
    .setStrokeSize(2f)
    .setStrokeColor(R.color.white)
    .intoBackground()
```

**Ví dụ với Text Builder:**
```kotlin
binding.shapeButton.textColorBuilder
    .setTextColor(R.color.purple_200)
    .setTextStrokeSize(1f)
    .setTextStrokeColor(R.color.white)
    .intoTextColor()
```

**Lưu ý:**
- Các phương thức setter tương ứng với các attributes trong XML
- Phải gọi `intoBackground()` để apply config vào background của View
- Phải gọi `intoTextColor()` để apply config vào text của View

---

# Ripple Shape View (Extended)

## Tổng quan

Kế thừa tất cả tính năng của Shape View, thêm hiệu ứng Ripple và Scale khi click.

## Các View được hỗ trợ

Thêm tiền tố `Ripple` vào các `ShapeView`:
- `RippleShapeButton`
- `RippleShapeConstraintLayout`
- `RippleShapeFrameLayout`
- `RippleShapeLinearLayout`
- `RippleShapeRelativeLayout`

## Thuộc tính bổ sung

*Ngoài tất cả thuộc tính của Shape View, còn có thêm:*

| Thuộc tính | Kiểu | Mô tả |
|-----------|------|-------|
| `app:shape_ripple_color` | color | Màu hiệu ứng ripple |
| `app:shape_ripple_radius` | dimension | Radius hiệu ứng; mặc định ăn theo radius được set ở trên, nếu set giá trị thì ghi đè |
| `app:scale_on_press` | boolean | Kích hoạt effect scale khi ấn; mặc định là `false` |

## Ví dụ XML
```xml
<com.wrapper.view.RippleShapeButton
    android:id="@+id/rippleButton"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:gravity="center"
    android:padding="16dp"
    android:text="Ripple Button"

    app:shape_radius="12dp"
    app:shape_solidColor="#6200EE"
    app:shape_strokeSize="2dp"
    app:shape_strokeColor="#FFFFFF"

    app:shape_ripple_color="#80FFFFFF"
    app:shape_ripple_radius="12dp"
    app:scale_on_press="true" />
```

## Sử dụng trong code

Có các builder tương tự như `ShapeView`, thêm các phương thức cho ripple:
```kotlin
binding.rippleButton.setRipplerRadius(14f)
binding.rippleButton.setRipplerColor(0x80FFFFFF.toInt())
```

**Lưu ý:**
- RippleShapeView kế thừa toàn bộ tính năng của ShapeView
- Có thể sử dụng tất cả các builder của ShapeView
- Ripple radius mặc định sẽ theo shape radius nếu không set riêng

---

# Utils & Extensions

## Tổng quan

Thư viện cung cấp các extension functions để thêm hiệu ứng cho bất kỳ View nào, không nhất thiết phải là ShapeView.

## Scale On Press Effect

### Mô tả

Tạo hiệu ứng thu nhỏ (scale down) khi người dùng ấn vào View, tự động phóng to về kích thước ban đầu khi thả ra.

### Kích hoạt
```kotlin
fun View.enableScaleOnPress(
    scaleFactor: Float = 0.95f,
    duration: Long = 100L
)
```

**Tham số:**

| Tham số | Kiểu | Mặc định | Mô tả |
|---------|------|----------|-------|
| `scaleFactor` | Float | 0.95f | Tỷ lệ scale khi ấn (< 1.0f để thu nhỏ) |
| `duration` | Long | 100L | Thời gian animation (milliseconds) |

**Ví dụ:**
```kotlin
binding.myButton.enableScaleOnPress()

binding.myImageView.enableScaleOnPress(
    scaleFactor = 0.9f,
    duration = 150L
)
```

### Vô hiệu hóa
```kotlin
fun View.disableScaleOnPress()
```

**Ví dụ:**
```kotlin
binding.myButton.disableScaleOnPress()
```

## Ripple Effect

### Mô tả

Thêm hiệu ứng Ripple cho bất kỳ View nào với khả năng tùy chỉnh radius từng góc.

### Kích hoạt

**Overload 1: Tùy chỉnh từng góc**
```kotlin
fun View.enableRippleOnClick(
    topLeft: Float = 0f,
    topRight: Float = 0f,
    bottomRight: Float = 0f,
    bottomLeft: Float = 0f,
    rippleColor: Int = 0x1F000000
)
```

**Tham số:**

| Tham số | Kiểu | Mặc định | Mô tả |
|---------|------|----------|-------|
| `topLeft` | Float | 0f | Radius góc trên trái |
| `topRight` | Float | 0f | Radius góc trên phải |
| `bottomRight` | Float | 0f | Radius góc dưới phải |
| `bottomLeft` | Float | 0f | Radius góc dưới trái |
| `rippleColor` | Int | 0x20FFFFFF | Màu hiệu ứng ripple (ARGB) |

**Ví dụ:**
```kotlin
binding.myView.enableRippleOnClick(
    topLeft = 12f,
    topRight = 12f,
    bottomRight = 0f,
    bottomLeft = 0f,
    rippleColor = 0x80FFFFFF.toInt()
)
```

**Overload 2: Radius đồng nhất**
```kotlin
fun View.enableRippleOnClick(
    radius: Float = 0f,
    rippleColor: Int = 0x1F000000
)
```

**Tham số:**

| Tham số | Kiểu | Mặc định | Mô tả |
|---------|------|----------|-------|
| `radius` | Float | 0f | Radius cho tất cả 4 góc |
| `rippleColor` | Int | 0x20FFFFFF | Màu hiệu ứng ripple |

**Ví dụ:**
```kotlin
binding.myButton.enableRippleOnClick()

binding.myCardView.enableRippleOnClick(
    radius = 16f,
    rippleColor = 0x33FF5722
)
```

## Lưu ý

- Extension functions có thể áp dụng cho **bất kỳ View nào**, không chỉ ShapeView
- `enableScaleOnPress()` tự động set `isClickable = true` và `isFocusable = true`
- `enableRippleOnClick()` tự động set `isClickable = true`