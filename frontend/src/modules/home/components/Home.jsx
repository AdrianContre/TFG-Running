import background from '../../../assets/images/background.png'
import Button from 'react-bootstrap/Button';
import '../styles/home.css'
import {Link} from 'react-router-dom'

function Home() {
    return (
      <div className='main' style={{backgroundImage: `url(${background})`}}>
        <div className='d-flex align-items-center justify-content-center' style={{marginTop: '60px'}}>
          <p className='title'>RUNNING2ALL</p>
        </div>
        <div className='button-container d-grid gap-2'>
          <Link to='/login'>
            <Button variant='primary' size='lg' className='mb-5'>Iniciar sesión</Button>
          </Link>
          <Link to='/register'>
            <Button variant='primary' size='lg'>Registrate ahora</Button>
          </Link>
        </div>
      </div>
    );
}

export default Home;