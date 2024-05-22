<script lang="ts" setup>
definePageMeta({
  middleware: "auth",
});
const route = useRoute();
</script>

<template>
  {{ $route.params.id }}
</template>
