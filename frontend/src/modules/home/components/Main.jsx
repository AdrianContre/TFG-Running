import { useNavigate } from 'react-router';
import { getUserLogged } from '../services/mainService';
import NavigationBar from './NavigationBar';
import { useEffect } from 'react';
import { useState } from 'react';

const Main = () => {
    const navigate = useNavigate()
    const [userLoaded, setUserLoaded] = useState(false)

    useEffect (() => {
        const getUserAuth = async () => {
            const user = await getUserLogged();
            localStorage.setItem('userAuth', JSON.stringify(user));
            setUserLoaded(true);
        }
        getUserAuth()
    },[])
    
    return (
        <>
            {userLoaded && <NavigationBar />}
            <div className='container'>
               <p className='text-custom'> BIENVENIDO DE NUEVO!!
                DESPLAZATE POR LAS 
                OPCIONES DE LA 
                PLATAFORMA </p>
            </div>
        </>
    )
}

export default Main;