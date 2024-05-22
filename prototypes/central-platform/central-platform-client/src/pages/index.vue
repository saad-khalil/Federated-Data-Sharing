<script setup lang="ts">
import { useToast } from "vue-toastification";
import { useUserStore } from "~/stores/user/userStore";
import type { UserCredentials } from "~/types/User/User";

definePageMeta({
  layout: "auth",
});

onMounted(() => {
  const toast = useToast();

  if (useCookie("userToken").value) {
   
    navigateTo("/home");
    setTimeout(() => {
      toast.info("Already authenticated, Redirecting");
    }, 600);
  }
});

const toast = useToast();

async function login() {
  setTimeout(() => {
    toast.info("Reading Credentials");
  });

  const userStore = useUserStore();
  const { authenticate } = userStore;

  setTimeout(() => {
    toast.info("Checking authentication");
  }, 800);

  await authenticate(credentials.value);

  if (useCookie("userToken").value) {
    setTimeout(() => {
      toast.success("Connecting");
    }, 1400);

    setTimeout(() => {
      navigateTo("/home");
    }, 2000);
  } else {
    toast.error("Could not authenticate");
  }
}

const credentials = ref<UserCredentials>({
  email: "",
  password: "",
});
</script>

<template>
  <div class="max-h-screen">
    <section
      class="bg-trnasparent min-h-screen flex items-center justify-center"
    >
      <div
        class="bg-neutral-800 p-5 flex rounded-2xl shadow-3xl border-4 border-emerald-300/10 max-w-3xl"
      >
        <div class="md:w-1/2 px-5 pt-10">
          <h2 class="text-3xl font-bold text-white">Login</h2>
          <div class="mt-6">
            <div>
              <label class="block text-neutral-300">Email Address</label>
              <input
                type="email"
                v-model="credentials.email"
                placeholder="Enter Email Address"
                class="w-full px-4 py-3 rounded-lg bg-neutral-100 mt-2 border-4 border-transparent focus:border-emerald-400 focus:bg-white focus:outline-none transition duration-300"
                autofocus
                required
              />
            </div>

            <div class="mt-4">
              <label class="block text-neutral-300">Password</label>
              <input
                type="password"
                v-model="credentials.password"
                placeholder="Enter Password"
                minlength="6"
                class="w-full px-4 py-3 rounded-lg bg-neutral-100 mt-2 border-4 focus:border-emerald-400 transition duration-300 focus:bg-white focus:outline-none"
                required
              />
            </div>
            <button
              @click="login"
              type="submit"
              class="w-full block bg-emerald-500 hover:bg-emerald-400 focus:bg-emerald-600 text-white font-semibold rounded-lg transition duration-300 px-4 py-3 mt-6 text-center"
            >
              Login
            </button>
          </div>

          <div class="mt-7 grid grid-cols-3 items-center text-gray-500">
            <hr class="border-gray-500" />
            <p class="text-center text-sm">OR</p>
            <hr class="border-gray-500" />
          </div>

          <NuxtLink
            to="/register"
            class="bg-neutral-900 py-2 w-full rounded-xl mt-5 flex justify-center items-center text-sm hover:scale-105 duration-300"
          >
            <span class="ml-4 text-white font-semibold">Register</span>
          </NuxtLink>
        </div>

        <div class="w-1/2 md:block hidden">
          <img
            src="https://images.unsplash.com/photo-1614741118887-7a4ee193a5fa?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1887&q=80"
            class="rounded-2xl"
            alt="page img"
          />
        </div>
      </div>
    </section>
  </div>
</template>
