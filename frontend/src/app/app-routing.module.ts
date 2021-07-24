import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RoomListComponent } from './components/room-list/room-list.component';
import { RoomDetailsComponent } from './components/room-details/room-details.component';
import { AddRoomComponent } from './components/add-room/add-room.component';

const routes: Routes = [
  { path: '', redirectTo: 'rooms', pathMatch: 'full'},
  { path: 'rooms', component: RoomListComponent},
  { path: 'rooms/:id', component: RoomDetailsComponent},
  { path: 'add', component: AddRoomComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
