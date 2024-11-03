import background from './assets/images/background.png'
import Button from 'react-bootstrap/Button';
import './App.css'
import {Link, Route, Routes} from 'react-router-dom'
import Login from './modules/auth/components/Login';
import Register from './modules/auth/components/Register';
import Main from './modules/home/components/Main';
import EditProfile from './modules/profile/components/editProfile';
import ViewProfile from './modules/profile/components/viewProfile';
import ListMaterials from './modules/profile/components/ListMaterials';
import CreateMaterial from './modules/profile/components/CreateMaterial';
import EditMaterial from './modules/profile/components/EditMaterial';
import ListActivities from './modules/activities/components/ListActivities';
import CreateActivity from './modules/activities/components/CreateActivity';
import ViewManualActivity from './modules/activities/components/ViewManualActivity';
import EditManualActivity from './modules/activities/components/EditManualActivity';



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


function App() {
  return (
      <Routes>
        <Route path='/editmanualactivity' element={<EditManualActivity />} />
        <Route path='/viewmanualactivity' element={<ViewManualActivity />} />
        <Route path='/createactivity' element={<CreateActivity />} />
        <Route path='/activities' element={<ListActivities />} />
        <Route path='/editmaterial' element={<EditMaterial />} />
        <Route path='/creatematerial' element={<CreateMaterial />} />
        <Route path='/listmaterials' element={<ListMaterials/>} />
        <Route path='/profile' element={<ViewProfile/>} />
        <Route path='/editprofile' element={<EditProfile/>} />
        <Route path='/login' element={<Login/>} />
        <Route path='/register' element={<Register/>} />
        <Route path='/main' element={<Main/>} />
        <Route path='/' element={<Home/>} />
      </Routes>
  )
}

export default App
