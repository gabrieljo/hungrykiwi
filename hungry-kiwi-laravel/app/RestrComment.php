<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class RestrComment extends Model
{
    protected $fillable = [
        'id',
        'user_id',
        'restr_id',
        'rate',
        'comment',
        'created_at',
        'updated_at',
    ];
}
