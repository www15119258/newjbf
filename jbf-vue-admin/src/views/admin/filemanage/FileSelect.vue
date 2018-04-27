<template>
<workspace :selectBtnEnable="true" :renameBtnEnable="true" :copyBtnEnable="true" :cutBtnEnable="true" :pasteBtnEnable="true" :delBtnEnable="true"></workspace>
</template>

<script>
import Workspace from './Workspace.vue'

export default {
  components: {
    Workspace
  },
  data () {
    return {
    }
  },
  mounted: function () {
  },
  destroyed: function () {
  }
}
</script>

<style scoped>
</style>
