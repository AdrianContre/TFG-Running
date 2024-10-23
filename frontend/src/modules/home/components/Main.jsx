import { useNavigate } from 'react-router';
const Main = () => {
    const navigate = useNavigate()
    
    return (
        <div>
            <div className="d-flex justify-content-center" style={{marginTop:'25%'}}>
                Página de inicio
            </div>
        </div>
    )
}

export default Main;