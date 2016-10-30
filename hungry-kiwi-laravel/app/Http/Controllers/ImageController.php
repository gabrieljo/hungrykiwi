<?php

namespace App\Http\Controllers;

use App\Http\Util;
use Illuminate\Http\Request;

use App\Http\Requests;
use Intervention\Image\Facades\Image;

class ImageController extends Controller
{
    /**
     * access to post image
     * @param $imgs
     */
    public function post($imgs) {

        $fullPath = Util::getFullPath(Util::POST).$imgs;

        return Image::make($fullPath)->response();
    }
}
