# AppDeployStatus

Get information about the deploy status of an app

## Constructor

### **Parameter**


| Name| Type| Description |
| --- | --- | --- |
| app | Integer | The appId
| status | [Status](#status) | The status of the deployment of App settings.

## Methods

### getApp()

Get the appId

**Parameter**

(none)

**Return**

Integer

### getStatus()

The status of the deployment of App settings.

**Parameter **

(none)

**Return**

String

## Enum

### Status

| Name | Type | Value | Description |
| --- | --- | --- | --- |
| PROCESSING | String | PROCESSING | The App settings are being deployed.
| SUCCESS | String | SUCCESS | The App settings have been deployed.
| FAIL | String | FAIL | An error occurred, and the deployment of App settings failed.
| CANCEL | String | CANCEL | The deployment of App settings was canceled, due to the deployment of other App settings failing.