package com.example.vndbviewer.presentation.adapters

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.vndbviewer.R
import com.example.vndbviewer.databinding.ScreenshotImgBinding


class ScreenshotImgAdapter :
    ListAdapter<Pair<String, Double>, ScreenshotImgAdapter.ScreenshotImgViewHolder>(DiffCallback) {

    // Hold a reference to the current animator so that it can be canceled
    // midway.
    private var currentAnimator: Animator? = null

    // The system "short" animation time duration in milliseconds. This duration
    // is ideal for subtle animations or animations that occur frequently.
    private var shortAnimationDuration: Int = 0

    class ScreenshotImgViewHolder(val binding: ScreenshotImgBinding) :
        RecyclerView.ViewHolder(binding.root)

    private object DiffCallback : DiffUtil.ItemCallback<Pair<String, Double>>() {
        override fun areItemsTheSame(
            oldItem: Pair<String, Double>,
            newItem: Pair<String, Double>
        ): Boolean {
            return oldItem.first == newItem.first
        }

        override fun areContentsTheSame(
            oldItem: Pair<String, Double>,
            newItem: Pair<String, Double>
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenshotImgViewHolder {
        val binding = ScreenshotImgBinding.inflate(LayoutInflater.from(parent.context))
        return ScreenshotImgViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScreenshotImgViewHolder, position: Int) {
        val img = getItem(position)
        holder.binding.screenshotImg.load(img.first) {
            crossfade(true)
            crossfade(250)
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
            Log.d("img", img.first)
        }
        holder.binding.screenshotImg.setOnClickListener {
            zoomImage(img.first, holder)
        }
        //Log.d("img", currentList.toString())
    }

    private fun zoomImage(imageUrl: String, holder: ScreenshotImgViewHolder) {
        // If there's an animation in progress, cancel it immediately and
        // proceed with this one.
        currentAnimator?.cancel()

        // Load the high-resolution "zoomed-in" image.
        holder.binding.expandedImage.load(imageUrl.replace("/st/", "/sf/")) {
            crossfade(true)
            crossfade(250)
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }

        // Calculate the starting and ending bounds for the zoomed-in image.
        val startBoundsInt = Rect()
        val finalBoundsInt = Rect()
        val globalOffset = Point()

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the
        // container view. Set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        holder.binding.screenshotImg.getGlobalVisibleRect(startBoundsInt)
        holder.binding.imageContainer.getGlobalVisibleRect(finalBoundsInt, globalOffset)
        startBoundsInt.offset(-globalOffset.x, -globalOffset.y)
        finalBoundsInt.offset(-globalOffset.x, -globalOffset.y)

        val startBounds = RectF(startBoundsInt)
        val finalBounds = RectF(finalBoundsInt)

        // Using the "center crop" technique, adjust the start bounds to be the
        // same aspect ratio as the final bounds. This prevents unwanted
        // stretching during the animation. Calculate the start scaling factor.
        // The end scaling factor is always 1.0.
        val startScale: Float
        if ((finalBounds.width() / finalBounds.height() > startBounds.width() / startBounds.height())) {
            // Extend start bounds horizontally.
            startScale = startBounds.height() / finalBounds.height()
            val startWidth: Float = startScale * finalBounds.width()
            val deltaWidth: Float = (startWidth - startBounds.width()) / 2
            startBounds.left -= deltaWidth.toInt()
            startBounds.right += deltaWidth.toInt()
        } else {
            // Extend start bounds vertically.
            startScale = startBounds.width() / finalBounds.width()
            val startHeight: Float = startScale * finalBounds.height()
            val deltaHeight: Float = (startHeight - startBounds.height()) / 2f
            startBounds.top -= deltaHeight.toInt()
            startBounds.bottom += deltaHeight.toInt()
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it positions the zoomed-in view in the place of the
        // thumbnail.
        holder.binding.screenshotImg.alpha = 0f

        animateZoomToLargeImage(startBounds, finalBounds, startScale, holder)

        setDismissLargeImageAnimation(startBounds, startScale, holder)
    }

    private fun animateZoomToLargeImage(
        startBounds: RectF,
        finalBounds: RectF,
        startScale: Float,
        holder: ScreenshotImgViewHolder
    ) {
        holder.binding.expandedImage.visibility = View.VISIBLE

        // Set the pivot point for SCALE_X and SCALE_Y transformations to the
        // top-left corner of the zoomed-in view. The default is the center of
        // the view.
        holder.binding.expandedImage.pivotX = 0f
        holder.binding.expandedImage.pivotY = 0f

        // Construct and run the parallel animation of the four translation and
        // scale properties: X, Y, SCALE_X, and SCALE_Y.
        currentAnimator = AnimatorSet().apply {
            play(
                ObjectAnimator.ofFloat(
                    holder.binding.expandedImage,
                    View.X,
                    startBounds.left,
                    finalBounds.left
                )
            ).apply {
                with(
                    ObjectAnimator.ofFloat(
                        holder.binding.expandedImage,
                        View.Y,
                        startBounds.top,
                        finalBounds.top
                    )
                )
                with(
                    ObjectAnimator.ofFloat(
                        holder.binding.expandedImage,
                        View.SCALE_X,
                        startScale,
                        1f
                    )
                )
                with(
                    ObjectAnimator.ofFloat(
                        holder.binding.expandedImage,
                        View.SCALE_Y,
                        startScale,
                        1f
                    )
                )
            }
            duration = shortAnimationDuration.toLong()
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator) {
                    currentAnimator = null
                }

                override fun onAnimationCancel(animation: Animator) {
                    currentAnimator = null
                }
            })
            start()
        }
    }

    private fun setDismissLargeImageAnimation(
        startBounds: RectF,
        startScale: Float,
        holder: ScreenshotImgViewHolder
    ) {
        // When the zoomed-in image is tapped, it zooms down to the original
        // bounds and shows the thumbnail instead of the expanded image.
        holder.binding.expandedImage.setOnClickListener {
            currentAnimator?.cancel()

            // Animate the four positioning and sizing properties in parallel,
            // back to their original values.
            currentAnimator = AnimatorSet().apply {
                play(
                    ObjectAnimator.ofFloat(
                        holder.binding.expandedImage,
                        View.X,
                        startBounds.left
                    )
                ).apply {
                    with(
                        ObjectAnimator.ofFloat(
                            holder.binding.expandedImage,
                            View.Y,
                            startBounds.top
                        )
                    )
                    with(
                        ObjectAnimator.ofFloat(
                            holder.binding.expandedImage,
                            View.SCALE_X,
                            startScale
                        )
                    )
                    with(
                        ObjectAnimator.ofFloat(
                            holder.binding.expandedImage,
                            View.SCALE_Y,
                            startScale
                        )
                    )
                }
                duration = shortAnimationDuration.toLong()
                interpolator = DecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter() {

                    override fun onAnimationEnd(animation: Animator) {
                        holder.binding.screenshotImg.alpha = 1f
                        holder.binding.expandedImage.visibility = View.GONE
                        currentAnimator = null
                    }

                    override fun onAnimationCancel(animation: Animator) {
                        holder.binding.screenshotImg.alpha = 1f
                        holder.binding.expandedImage.visibility = View.GONE
                        currentAnimator = null
                    }
                })
                start()
            }
        }
    }
}