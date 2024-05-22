export default defineNuxtRouteMiddleware((to, from) => {
    const isAuthenticated = () => {
      const userToken = useCookie('userToken');
      return ((userToken.value !== null) && (userToken.value !== undefined));
    };
 
    if (!isAuthenticated()) {
      return navigateTo('/');
    }
  });