terraform {
  backend "s3" {
    bucket = "fiap-hackaton-video-bucket"
    key    = "deployment-service/terraform.tfstate"
    region = "us-east-1"
  }
}

module "msuservideomanager" {
  source = "./infra"

  project_name    = var.projectname
  region          = var.aws_region
  appversion      = var.app_version
  bucket          = var.buckets3
  accesskey       = var.access_key
  cluster         = var.cluster_name
  accessSecretKey = var.access_secret_key
  cognitoJwkUri   = var.cognito_jwk_uri
  mongodbUrl      = var.mongodb_url
  cognitoUserPool = var.cognito_user_pool
  cognitoClientId = var.cognito_client_id
  cognitoSecret   = var.cognito_secret
}

variable "aws_region" {
  type        = string
  default     = "us-east-1"
  description = "AWS region"
}

variable "app_version" {
  type        = string
  description = "version to deploy"
}

variable "projectname" {
  type        = string
  default     = "videoprocessor"
  description = "Application Name"
}

variable "cluster_name" {
  type        = string
  default     = "hackaton"
  description = "cluster Name"
}

variable "buckets3" {
  type        = string
  description = "bucket name"
}

variable "access_key" {
  type        = string
  description = "access key"
}

variable "access_secret_key" {
  type        = string
  description = "access secret key"
}

variable "cognito_jwk_uri" {
  type        = string
  description = "cognito jwk url"
}

variable "mongodb_url" {
  type        = string
  description = "mongodb url"
}

variable "cognito_user_pool" {
  type        = string
  description = "cognito user pool"
}

variable "cognito_client_id" {
  type        = string
  description = "cognito client id"
}

variable "cognito_secret" {
  type        = string
  description = "cognito secret"
}