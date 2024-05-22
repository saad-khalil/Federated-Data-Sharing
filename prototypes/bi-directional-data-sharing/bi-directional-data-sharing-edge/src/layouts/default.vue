<script setup lang="ts">

import { useDatabase } from "../database/database"

</script>


<template>
    <main class="h-screen w-full bg-neutral-900 overflow-hidden">
        <LazyNavbar />
        <NuxtPage/>
    </main>
</template>