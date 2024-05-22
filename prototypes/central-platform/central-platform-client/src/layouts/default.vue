<script setup lang="ts">

</script>

<template>
    <main class="flex flex-col h-screen overflow-hidden bg-neutral-900">
      <LazyNavbar class="sticky top-0" />
      <div class="overflow-y-auto">
        <NuxtPage class="flex-1" />
      </div>
    </main>
  </template>