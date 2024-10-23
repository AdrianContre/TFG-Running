import { useNavigate } from 'react-router';
import NavigationBar from './NavigationBar';

const Main = () => {
    const navigate = useNavigate()
    
    return (
        <>
            <NavigationBar/>
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