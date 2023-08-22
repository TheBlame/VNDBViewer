package com.example.vndbviewer.domain

data class ScreenshotList(
    val title: String,
    val releaseId: String,
    val screenshotList: List<Pair<String, Double>>
)