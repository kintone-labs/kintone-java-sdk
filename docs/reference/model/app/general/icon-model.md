# Icon
The Icon of an apps.

## Constructor

**Declaration**
```
public Icon(FileModel file, String key, IconType type) 
```

**Parameter**

| Name|Description |
| --- | --- | --- |
| key | The key of the Icon
| iconType | The icon type of the Icon ([IconType](#icontype))
| file | The file of the Icon ([FileModel](/reference/model/file/file-model))

## Methods

### getFile

The file of the Icon

**Declaration**
```
public FileModel getFile()
```

**Parameter**

(none)

### getKey

The key of the Icon

**Declaration**
```
public String getKey()
```

**Parameter**

(none)

### getIconType

The icon type of the Icon

**Declaration**
```
public IconType getType()
```
**Parameter**

(none)

## Enum

### IconType

| Name | Type | Value 
| --- | --- | --- |
| FILE | String | FILE 
| PRESET | String | PRESET