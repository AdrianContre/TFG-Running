const END_POINT_MATERIALS = "http://localhost:8081/api/v1/materials"


export const getUserMaterials = async (id) => {
    const authHeader = `Bearer ${localStorage.getItem('token')}`
    return fetch(`${END_POINT_MATERIALS}/runners/${id}`,{
        method: 'GET',
        headers: {
        'Authorization': authHeader,
        'Content-Type': 'application/json',
        }
    })
    .then(response => {
        return response.json();
      })
      .then(data => {
        console.log(data);
        return data.data
      })
      .catch(error => {
        console.error('Error:', error);
        throw error; // Lanzar el error para que pueda ser manejado por quien llame a esta función
      });
}