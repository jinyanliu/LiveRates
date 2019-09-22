package com.lung.demo.domain.exception

class IllegalParamException(params: String) : Exception("The params:$params can't be negative!")
