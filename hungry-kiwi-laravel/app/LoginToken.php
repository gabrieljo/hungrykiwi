<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class LoginToken extends Model {
    protected $fillable = [
        'user_id', 'token',
    ];

    public static function getUserBy($token) {
    	$login_token = static::where('token', $token) -> first();
    	return User::where('id', '=', $login_token -> user_id) -> first();
    }
}
