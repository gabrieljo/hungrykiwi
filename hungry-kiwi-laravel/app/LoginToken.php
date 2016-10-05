<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class LoginToken extends Model {
    protected $fillable = [
        'user_id', 'token',
    ];
}
