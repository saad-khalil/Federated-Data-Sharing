<script setup lang="ts">
    import { useUserStore } from '~/stores/User/userStore';

    const userStore = useUserStore();
    const { getUser } = userStore;
    const user = getUser
    const isSidemenuOpen : Ref<boolean> = ref<boolean>(false);

</script>

<template>
    <div>
        <div class=" w-full flex flex-col max-h-screen">
            <div class="w-full flex flex-row overflow-hidden">
                <LazySidebar />
                <LazyNuxtPage class="w-full bg-neutral-900 min-h-screen h-full"/>
                <LazyControllbar
                @update:slide="isSidemenuOpen = true" />
                <LazySlideover
                title="Account Information"
                :is-slide-open="isSidemenuOpen"
                @update:slide-open="isSidemenuOpen = $event"
                >
                <div class="flex flex-col items-start justify-center gap-4 w-full">
                    <h1 class="text-white font-semibold text-[30px] pb-10">Hello {{ user.name }}!</h1>
                    <div class="bg-neutral-900 text-white w-full flex flex-col p-3 gap-2 rounded-md">
                        <h1 class="font-semibold">Organization:</h1>
                        <h1 class="bg-neutral-950 p-2 rounded-md">Some organization name</h1>
                    </div>

                    <div class="bg-neutral-900 text-white w-full flex flex-col p-3 gap-2 rounded-md">
                        <h1 class="font-semibold">Organization:</h1>
                        <h1 class="bg-neutral-950 p-2 rounded-md">Some organization name</h1>
                    </div>

                    <div class="bg-neutral-900 text-white w-full flex flex-col p-3 gap-2 rounded-md">
                        <h1 class="font-semibold">Organization:</h1>
                        <h1 class="bg-neutral-950 p-2 rounded-md">Some organization name</h1>
                    </div>

                    <div class="bg-neutral-900 text-white w-full flex flex-col p-3 gap-2 rounded-md">
                        <h1 class="font-semibold">Organization:</h1>
                        <h1 class="bg-neutral-950 p-2 rounded-md">Some organization name</h1>
                    </div>
                </div>
                </LazySlideover>
            </div>
        </div>
    </div>
</template>