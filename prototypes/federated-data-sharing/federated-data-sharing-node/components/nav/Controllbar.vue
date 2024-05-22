<script lang="ts" setup>
    import { HomeModernIcon, AdjustmentsHorizontalIcon, Square3Stack3DIcon, Squares2X2Icon } from "@heroicons/vue/24/solid"

    defineEmits(['update:slide'])

    const isPageOpen :  Ref<boolean> = ref<boolean>(false)
</script>

<template>
    <div>
        <aside class="h-16 w-[400px] absolute left-1/2 -translate-x-1/2 bottom-10 bg-emerald-500 flex flex-row items-center justify-evenly rounded-md shadow-md z-[99999]">

            <LazyNuxtLink
            to="/home"
            class="w-12 text-white p-2 border-2 border-transparent hover:border-emerald-300 hover:bg-emerald-400 transition duration-300 rounded-md">
                <HomeModernIcon />
            </LazyNuxtLink>
            <LazyNuxtLink
            to="/database"
            class="w-12 text-white p-2 border-2 border-transparent hover:border-emerald-300 hover:bg-emerald-400 transition duration-300 rounded-md">
                <Square3Stack3DIcon />
            </LazyNuxtLink>
            <LazyNuxtLink
            to="/dashboard"
            class="w-12 text-white p-2 border-2 border-transparent hover:border-emerald-300 hover:bg-emerald-400 transition duration-300 rounded-md">
                <Squares2X2Icon  />
            </LazyNuxtLink>
            <div
            @click="$emit('update:slide', !isPageOpen)"
            class="w-12 text-white p-2 border-2 border-transparent hover:border-emerald-300 hover:bg-emerald-400 transition duration-300 rounded-md"

            >
                <AdjustmentsHorizontalIcon />
            </div>
        </aside>
    </div>
</template>

<style lang="scss" scoped>
</style>
