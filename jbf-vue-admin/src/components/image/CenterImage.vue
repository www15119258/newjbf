<template>
  <div class="center-img-div" :style="'height: ' + height">
    <img v-lazy="src" class="center-img" @click="previewImg"/>
    <vue-gallery :images="images" :index="imageIndex" @close="imageIndex = null"></vue-gallery>
  </div>
</template>

<script>
import VueGallery from 'vue-gallery'

export default {
  components: {
    VueGallery
  },
  props: {
    height: {
      type: [String],
      default: '0'
    },
    src: {
      type: [String],
      default: ''
    }
  },
  data () {
    return {
      images: [],
      imageIndex: null
    }
  },
  methods: {
    previewImg: function () {
      this.images = [this.src]
      this.imageIndex = 0
    }
  }
}
</script>

<style scoped>
  .center-img-div {
    width: 100%;
    position: relative;
    text-align: center;
  }
  .center-img {
    max-width: 100%;
    max-height: 100%;
    position: absolute;
    left: 50%;
    top: 50%;
    text-align: center;
    transform: translate(-50%,-50%);
  }
</style>
