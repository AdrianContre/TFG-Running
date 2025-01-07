const API_ROUTE = "http://localhost:8081/api/v1/users/whoAmI"

export const getUserLogged = async () => {
    const authHeader = `Bearer ${localStorage.getItem('token')}`
    return fetch(API_ROUTE,{
        method: 'GET',
        headers: {
        'Authorization': authHeader,
        'Content-Type': 'application/json',
        },
    })
    .then(response => {
        return response.json();
      })
      .then(data => {
        console.log(data);
        return data.userLogged;
      })
      .catch(error => {
        console.error('Error:', error);
        throw error;
      });
}