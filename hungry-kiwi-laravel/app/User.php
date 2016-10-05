<?php

namespace App;

use Illuminate\Notifications\Notifiable;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Http\Request;
use App\LoginToken;
class User extends Authenticatable
{
    use Notifiable;

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'email', 'first_name', 'last_name', 'is_restr', 'img', 'cover_img', 'provider',
    ];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    protected $hidden = [
        'password', 'remember_token',
    ];

    public static function login(Request $req) {
        $user = User::where('email', '=', $req -> email) -> first();

        if(count($user) > 0) {
            return $user;
            
        } else {
            $info = [
                'email' => $req -> email,
                'first_name' => $req -> first_name,
                'last_name' => $req -> last_name,
                'is_restr' => $req -> is_restr,
                'img' => $req -> img,
                'cover_img' => $req -> cover_img,
                'provider' => $req -> provider,
            ];
            return User::create($info);
            
        }
    }



}
