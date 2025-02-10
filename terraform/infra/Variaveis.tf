variable "project_name" {
  type        = string
  description = "Application Name"
}

variable "region" {
  type        = string
  description = "AWS region"
}

variable "appversion" {
  type        = string
  description = "version to deploy"
}

variable "cluster" {
  type        = string
  description = "cluster eks"
}

variable "bucket" {
  type        = string
  description = "bucket name"
}

variable "accesskey" {
  type        = string
  description = "access key"
}

variable "accessSecretKey" {
  type        = string
  description = "access secret key"
}

variable "cognitoJwkUri" {
  type        = string
  description = "cognito jwk url"
}

variable "mongodbUrl" {
  type        = string
  description = "mongodb url"
}

variable "cognitoUserPool" {
  type        = string
  description = "cognito user pool"
}

variable "cognitoClientId" {
  type        = string
  description = "cognito client id"
}

variable "cognitoSecret" {
  type        = string
  description = "cognito secret"
}