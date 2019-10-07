# AppDeployStatus

Get information about the deploy status of an app

## Constructor

**Declaration**
```
public AppDeployStatus()
public AppDeployStatus(Integer app, Status status)
```

**Parameter**

| Name| Description |
| --- | --- |
| app | The appId
| status | The status of the deployment of App settings.

## Methods

### getApp

Get the appId

**Declaration**
```
public Integer getApp()
```

**Parameter**

(none)

### getStatus

The status of the deployment of App settings.

**Declaration**
```
public Status getStatus()
```

**Parameter**

(none)

## Enum

### Status

| Name | Type | Value | Description |
| --- | --- | --- | --- |
| PROCESSING | String | PROCESSING | The App settings are being deployed.
| SUCCESS | String | SUCCESS | The App settings have been deployed.
| FAIL | String | FAIL | An error occurred, and the deployment of App settings failed.
| CANCEL | String | CANCEL | The deployment of App settings was canceled, due to the deployment of other App settings failing.